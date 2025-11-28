package com.gestion.gestionalumnos.service;

import com.gestion.gestionalumnos.model.*;
import com.gestion.gestionalumnos.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar la lógica de inscripción (Matricula) y la base de las calificaciones.
 */
@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlumnoService alumnoService; // Para buscar el alumno
    private final CursoService cursoService;   // Para buscar el curso
    private final NucleoCalificacionService nucleoCalificacionService;

    @Autowired
    public MatriculaService(MatriculaRepository matriculaRepository,
                            AlumnoService alumnoService,
                            CursoService cursoService, NucleoCalificacionService nucleoClasificacionService) {
        this.matriculaRepository = matriculaRepository;
        this.alumnoService = alumnoService;
        this.cursoService = cursoService;
        this.nucleoCalificacionService = nucleoClasificacionService;
    }

    // 1. Lógica para crear una nueva matriculación
    public Matricula matricularAlumno(Long alumnoId, Long cursoId) {
        // Validación de existencia de Alumno y Curso
        Alumno alumno = alumnoService.obtenerPorId(alumnoId)
                .orElseThrow(() -> new IllegalArgumentException("Alumno con ID " + alumnoId + " no encontrado."));

        Curso curso = cursoService.obtenerPorId(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso con ID " + cursoId + " no encontrado."));

        // Lógica de Negocio: Evitar doble inscripción
        if (matriculaRepository.findByAlumnoIdAndCursoId(alumnoId, cursoId) != null) {
            throw new IllegalStateException("El alumno ya está matriculado en este curso.");
        }

        Matricula nuevaMatricula = new Matricula(alumno, curso);
        return matriculaRepository.save(nuevaMatricula);
    }

    // 2. Lógica para obtener una matrícula por ID
    public Optional<Matricula> obtenerPorId(Long id) {
        return matriculaRepository.findById(id);
    }

    // 3. Lógica para obtener todas las matrículas de un alumno
    public List<Matricula> obtenerMatriculasPorAlumno(Long alumnoId) {
        return matriculaRepository.findByAlumnoId(alumnoId);
    }

    // 4. Lógica para desmatricular (eliminar)
    public void desmatricularAlumno(Long matriculaId) {
        if (!matriculaRepository.existsById(matriculaId)) {
            throw new IllegalArgumentException("Matrícula con ID " + matriculaId + " no encontrada.");
        }
        // Nota: Al borrar la Matricula, por el `CascadeType.ALL` configurado
        // en NucleoCalificacion, se borrarán todos los núcleos y calificaciones asociadas.
        matriculaRepository.deleteById(matriculaId);
    }

    /**
     * Calcula la nota final ponderada de un alumno en un curso.
     * @param matriculaId ID de la matrícula.
     * @return BigDecimal con la nota final calculada.
     */
    public BigDecimal calcularNotaFinal(Long matriculaId) {

        Matricula matricula = obtenerPorId(matriculaId)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula con ID " + matriculaId + " no encontrada."));

        // 1. Obtener todos los núcleos de calificación asociados a esta matrícula
        List<NucleoCalificacion> nucleos = nucleoCalificacionService.obtenerNucleosPorMatricula(matriculaId);

        if (nucleos.isEmpty()) {
            return BigDecimal.ZERO; // No hay notas para calcular
        }

        BigDecimal notaFinalAcumulada = BigDecimal.ZERO;
        double sumaPonderacion = 0.0;

        // 2. Iterar sobre cada núcleo
        for (NucleoCalificacion nucleo : nucleos) {

            List<Calificacion> calificaciones = nucleo.getCalificaciones();

            if (calificaciones == null || calificaciones.isEmpty()) {
                continue; // Saltar si el núcleo no tiene notas
            }

            // 2.1. Calcular el promedio simple dentro del núcleo
            BigDecimal sumaNotasNucleo = BigDecimal.ZERO;
            for (Calificacion calificacion : calificaciones) {
                sumaNotasNucleo = sumaNotasNucleo.add(calificacion.getValor());
            }

            BigDecimal promedioNucleo = sumaNotasNucleo.divide(
                    new BigDecimal(calificaciones.size()),
                    2,
                    RoundingMode.HALF_UP
            );

            // 2.2. Aplicar la ponderación del núcleo al promedio
            if (nucleo.getPonderacion() != null) {
                BigDecimal ponderacion = BigDecimal.valueOf(nucleo.getPonderacion());

                // Nota Ponderada = Promedio * Ponderación
                BigDecimal notaPonderada = promedioNucleo.multiply(ponderacion);

                notaFinalAcumulada = notaFinalAcumulada.add(notaPonderada);
                sumaPonderacion += nucleo.getPonderacion();
            }
        }

        // 3. Devolver la nota final. Si la suma de ponderaciones no es 1.0,
        // normalizamos la nota final dividiendo por la suma de ponderaciones aplicadas.
        if (sumaPonderacion > 0) {
            return notaFinalAcumulada.divide(
                    BigDecimal.valueOf(sumaPonderacion),
                    2,
                    RoundingMode.HALF_UP
            );
        }

        return notaFinalAcumulada.setScale(2, RoundingMode.HALF_UP);
    }
}