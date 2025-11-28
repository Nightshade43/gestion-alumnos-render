package com.gestion.gestionalumnos.repository;

import com.gestion.gestionalumnos.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    // Buscar todas las calificaciones que pertenecen a un núcleo específico
    List<Calificacion> findByNucleoId(Long nucleoId);
}