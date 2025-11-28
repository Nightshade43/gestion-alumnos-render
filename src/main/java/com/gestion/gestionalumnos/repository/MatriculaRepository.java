package com.gestion.gestionalumnos.repository;

import com.gestion.gestionalumnos.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Matricula.
 */
@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    // Método de búsqueda: Encontrar todas las matrículas para un alumno específico
    // Spring Data JPA lo interpreta buscando en el objeto 'alumno' por su 'id'
    List<Matricula> findByAlumnoId(Long alumnoId);

    // Método de búsqueda: Encontrar una matrícula específica entre un alumno y un curso
    Matricula findByAlumnoIdAndCursoId(Long alumnoId, Long cursoId);
}