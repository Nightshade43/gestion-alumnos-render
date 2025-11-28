package com.gestion.gestionalumnos.controller;

import com.gestion.gestionalumnos.model.NucleoCalificacion;
import com.gestion.gestionalumnos.service.NucleoCalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nucleos")
@CrossOrigin(origins = "*")
public class NucleoCalificacionController {

    private final NucleoCalificacionService nucleoService;

    @Autowired
    public NucleoCalificacionController(NucleoCalificacionService nucleoService) {
        this.nucleoService = nucleoService;
    }

    // 1. Crear Núcleo de Calificación (POST)
    // URL: /api/nucleos?matriculaId=1
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NucleoCalificacion crearNucleo(@RequestParam Long matriculaId,
                                          @RequestBody NucleoCalificacion nucleo) {
        if (matriculaId == null) {
            throw new IllegalArgumentException("El parámetro 'matriculaId' es requerido.");
        }
        // El servicio valida la existencia de la matrícula
        return nucleoService.crearNucleo(matriculaId, nucleo);
    }

    // 2. Obtener núcleos por Matrícula (GET)
    // URL: /api/nucleos/matricula/1
    @GetMapping("/matricula/{matriculaId}")
    public List<NucleoCalificacion> obtenerPorMatricula(@PathVariable Long matriculaId) {
        return nucleoService.obtenerNucleosPorMatricula(matriculaId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NucleoCalificacion> obtenerPorId(@PathVariable Long id) {
        return nucleoService.obtenerPorId(id)
                .map(nucleo -> new ResponseEntity<>(nucleo, HttpStatus.OK)) // HTTP 200
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // HTTP 404
    }

    // 3. Eliminar Núcleo (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarNucleo(@PathVariable Long id) {
        nucleoService.eliminarNucleo(id);
    }
}