package com.gestion.gestionalumnos.service;

import com.gestion.gestionalumnos.model.Curso;
import com.gestion.gestionalumnos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que maneja la lógica de negocio para la entidad Curso.
 */
@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    @Autowired
    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    // Crear/Guardar
    public Curso crearCurso(Curso curso) {
        // Lógica de Negocio: Validar que el código del curso sea único
        if (cursoRepository.findByCodigo(curso.getCodigo()) != null) {
            throw new IllegalStateException("El código de curso '" + curso.getCodigo() + "' ya está registrado.");
        }
        return cursoRepository.save(curso);
    }

    // Obtener todos
    public List<Curso> obtenerTodos() {
        return cursoRepository.findAll();
    }

    // Obtener por ID
    public Optional<Curso> obtenerPorId(Long id) {
        return cursoRepository.findById(id);
    }

    // Actualizar
    public Curso actualizarCurso(Long id, Curso datosCurso) {
        return cursoRepository.findById(id)
                .map(cursoExistente -> {
                    cursoExistente.setNombre(datosCurso.getNombre());
                    cursoExistente.setDescripcion(datosCurso.getDescripcion());
                    // El código se puede actualizar si no existe otro con ese código
                    cursoExistente.setCodigo(datosCurso.getCodigo());

                    return cursoRepository.save(cursoExistente);
                })
                .orElseThrow(() -> new IllegalArgumentException("Curso con ID " + id + " no encontrado."));
    }

    // Eliminar
    public void eliminarCurso(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new IllegalArgumentException("Curso con ID " + id + " no existe.");
        }
        // Nota: En la lógica de negocio real, se requeriría eliminar primero las Matriculas
        // asociadas a este curso para evitar fallos de integridad referencial.
        cursoRepository.deleteById(id);
    }
}