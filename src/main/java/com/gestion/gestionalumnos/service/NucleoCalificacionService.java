package com.gestion.gestionalumnos.service;

import com.gestion.gestionalumnos.model.Matricula;
import com.gestion.gestionalumnos.model.NucleoCalificacion;
import com.gestion.gestionalumnos.repository.MatriculaRepository;
import com.gestion.gestionalumnos.repository.NucleoCalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NucleoCalificacionService {

    private final NucleoCalificacionRepository nucleoRepository;
    private final MatriculaRepository matriculaRepository;

    @Autowired
    public NucleoCalificacionService(NucleoCalificacionRepository nucleoRepository, MatriculaRepository matriculaRepository) {
        this.nucleoRepository = nucleoRepository;
        this.matriculaRepository = matriculaRepository;
    }

    // Lógica para crear un nuevo núcleo en una matrícula
    public NucleoCalificacion crearNucleo(Long matriculaId, NucleoCalificacion nuevoNucleo) {

        // 1. Verificar la existencia de la Matrícula usando el REPOSITORIO
        Optional<Matricula> matriculaOpt = matriculaRepository.findById(matriculaId);

        if (matriculaOpt.isEmpty()) {
            throw new IllegalArgumentException("Matrícula con ID " + matriculaId + " no encontrada.");
        }

        Matricula matricula = matriculaOpt.get();

        // 2. Asignar la relación bidireccional
        nuevoNucleo.setMatricula(matricula);

        // 3. Lógica de Negocio: Validar ponderación
        if (nuevoNucleo.getPonderacion() == null || nuevoNucleo.getPonderacion() < 0 || nuevoNucleo.getPonderacion() > 1.0) {
            throw new IllegalStateException("La ponderación debe ser un valor decimal entre 0.0 y 1.0.");
        }

        return nucleoRepository.save(nuevoNucleo);
    }

    public Optional<NucleoCalificacion> obtenerPorId(Long id) {
        return nucleoRepository.findById(id);
    }

    public List<NucleoCalificacion> obtenerNucleosPorMatricula(Long matriculaId) {
        return nucleoRepository.findByMatriculaId(matriculaId);
    }

    public void eliminarNucleo(Long id) {
        if (!nucleoRepository.existsById(id)) {
            throw new IllegalArgumentException("Núcleo de Calificación con ID " + id + " no existe.");
        }
        // Nota: Por la configuración CascadeType.ALL en el modelo,
        // al eliminar el núcleo se eliminan automáticamente todas las calificaciones hijas.
        nucleoRepository.deleteById(id);
    }
}