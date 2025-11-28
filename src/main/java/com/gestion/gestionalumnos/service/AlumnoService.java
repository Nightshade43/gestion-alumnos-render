package com.gestion.gestionalumnos.service;

import com.gestion.gestionalumnos.model.Alumno;
import com.gestion.gestionalumnos.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que maneja la lógica de negocio para la entidad Alumno.
 */
@Service
public class AlumnoService {

    // Inyección de la dependencia del Repositorio
    private final AlumnoRepository alumnoRepository;

    @Autowired
    public AlumnoService(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    // 1. Lógica para crear/guardar un nuevo alumno
    public Alumno crearAlumno(Alumno alumno) {
        // Lógica de Negocio: Podríamos validar que el email no exista ya
        if (alumnoRepository.findByEmail(alumno.getEmail()) != null) {
            throw new IllegalStateException("El email ya está registrado.");
        }
        return alumnoRepository.save(alumno);
    }

    // 2. Lógica para obtener todos los alumnos
    public List<Alumno> obtenerTodos() {
        return alumnoRepository.findAll();
    }

    // 3. Lógica para obtener un alumno por ID
    public Optional<Alumno> obtenerPorId(Long id) {
        // Optional<T> se usa para manejar la posible ausencia del objeto
        return alumnoRepository.findById(id);
    }

    // 4. Lógica para actualizar un alumno existente
    public Alumno actualizarAlumno(Long id, Alumno datosAlumno) {
        // Se busca el alumno existente para actualizarlo
        return alumnoRepository.findById(id)
                .map(alumnoExistente -> {
                    // Actualización de campos
                    alumnoExistente.setNombre(datosAlumno.getNombre());
                    alumnoExistente.setApellido(datosAlumno.getApellido());

                    // Nota: La validación de email debe ser más robusta aquí (ej. si cambia, no debe colisionar)
                    alumnoExistente.setEmail(datosAlumno.getEmail());

                    return alumnoRepository.save(alumnoExistente); // Guarda y retorna el objeto actualizado
                })
                .orElseThrow(() -> new IllegalArgumentException("Alumno con ID " + id + " no encontrado para actualizar."));
    }

    // 5. Lógica para eliminar un alumno
    public void eliminarAlumno(Long id) {
        // Lógica de Negocio: Se debería validar si tiene matrículas activas antes de eliminar
        if (!alumnoRepository.existsById(id)) {
            throw new IllegalArgumentException("Alumno con ID " + id + " no existe.");
        }
        alumnoRepository.deleteById(id);
    }
}