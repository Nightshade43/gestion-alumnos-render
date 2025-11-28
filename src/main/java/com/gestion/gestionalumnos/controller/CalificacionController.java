package com.gestion.gestionalumnos.controller;

import com.gestion.gestionalumnos.model.Calificacion;
import com.gestion.gestionalumnos.service.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    private final CalificacionService calificacionService;

    @Autowired
    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    // 1. Asignar Calificación a un Núcleo (POST)
    // URL: /api/calificaciones?nucleoId=1
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Calificacion asignarCalificacion(@RequestParam Long nucleoId,
                                            @RequestBody Calificacion calificacion) {
        if (nucleoId == null) {
            throw new IllegalArgumentException("El parámetro 'nucleoId' es requerido.");
        }
        // El servicio valida la existencia del núcleo y el valor de la nota
        return calificacionService.asignarCalificacion(nucleoId, calificacion);
    }

    // 2. Obtener Calificación por ID (GET)
    @GetMapping("/{id}")
    public Optional<Calificacion> obtenerPorId(@PathVariable Long id) {
        return calificacionService.obtenerPorId(id);
    }

    // 3. Eliminar Calificación (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCalificacion(@PathVariable Long id) {
        calificacionService.eliminarCalificacion(id);
    }
}