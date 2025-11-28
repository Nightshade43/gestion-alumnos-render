package com.gestion.gestionalumnos.repository;

import com.gestion.gestionalumnos.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Curso.
 */
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Método de búsqueda personalizado
    Curso findByCodigo(String codigo);
}