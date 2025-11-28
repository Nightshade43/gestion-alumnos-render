package com.gestion.gestionalumnos.service;

import com.gestion.gestionalumnos.model.Calificacion;
import com.gestion.gestionalumnos.model.NucleoCalificacion;
import com.gestion.gestionalumnos.repository.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final NucleoCalificacionService nucleoCalificacionService;

    @Autowired
    public CalificacionService(CalificacionRepository calificacionRepository,
                               NucleoCalificacionService nucleoCalificacionService) {
        this.calificacionRepository = calificacionRepository;
        this.nucleoCalificacionService = nucleoCalificacionService;
    }

    // Lógica para asignar una calificación a un núcleo existente
    public Calificacion asignarCalificacion(Long nucleoId, Calificacion nuevaCalificacion) {

        // 1. Verificar la existencia del Núcleo
        Optional<NucleoCalificacion> nucleoOpt = nucleoCalificacionService.obtenerPorId(nucleoId);

        if (nucleoOpt.isEmpty()) {
            throw new IllegalArgumentException("Núcleo de Calificación con ID " + nucleoId + " no encontrado.");
        }

        NucleoCalificacion nucleo = nucleoOpt.get();

        // 2. Asignar la relación bidireccional
        nuevaCalificacion.setNucleo(nucleo);

        // 3. Lógica de Negocio: Validar que el valor sea aceptable (e.g., entre 0 y 100)
        if (nuevaCalificacion.getValor().doubleValue() < 0 || nuevaCalificacion.getValor().doubleValue() > 100) {
            throw new IllegalStateException("El valor de la calificación debe estar entre 0 y 100.");
        }

        return calificacionRepository.save(nuevaCalificacion);
    }

    public Optional<Calificacion> obtenerPorId(Long id) {
        return calificacionRepository.findById(id);
    }

    public void eliminarCalificacion(Long id) {
        if (!calificacionRepository.existsById(id)) {
            throw new IllegalArgumentException("Calificación con ID " + id + " no existe.");
        }
        calificacionRepository.deleteById(id);
    }
}