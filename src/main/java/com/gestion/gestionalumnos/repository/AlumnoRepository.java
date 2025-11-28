package com.gestion.gestionalumnos.repository;

import com.gestion.gestionalumnos.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz de Repositorio para la entidad Alumno.
 * Extiende JpaRepository para obtener operaciones CRUD básicas.
 */
@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    // Spring Data JPA generará automáticamente la implementación
    // de todos los métodos CRUD (save, findById, findAll, delete, etc.)

    // Podemos añadir métodos de consulta personalizados aquí, ej:
    Alumno findByEmail(String email);
}