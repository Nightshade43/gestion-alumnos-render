package com.gestion.gestionalumnos.repository;

import com.gestion.gestionalumnos.model.NucleoCalificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NucleoCalificacionRepository extends JpaRepository<NucleoCalificacion, Long> {

    // Buscar todos los núcleos de calificación para una matrícula específica
    List<NucleoCalificacion> findByMatriculaId(Long matriculaId);
}