package com.gestion.gestionalumnos.controller;

import com.gestion.gestionalumnos.model.Alumno;
import com.gestion.gestionalumnos.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para manejar las peticiones HTTP relacionadas con Alumnos.
 */
@RestController // Combina @Controller y @ResponseBody
@RequestMapping("/api/alumnos") // Mapea todas las peticiones a esta ruta base
@CrossOrigin(origins = "*")
public class AlumnoController {

    private final AlumnoService alumnoService;

    @Autowired
    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    // 1. Crear un nuevo Alumno (POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retorna HTTP 201
    public Alumno crearAlumno(@RequestBody Alumno alumno) {
        // La lógica de negocio está en el servicio (ej. validación de email)
        return alumnoService.crearAlumno(alumno);
    }

    // 2. Obtener todos los Alumnos (GET)
    @GetMapping
    public List<Alumno> obtenerTodos() {
        return alumnoService.obtenerTodos();
    }

    // 3. Obtener un Alumno por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtenerPorId(@PathVariable Long id) {
        // Usamos ResponseEntity para controlar el código de estado HTTP
        return alumnoService.obtenerPorId(id)
                .map(alumno -> new ResponseEntity<>(alumno, HttpStatus.OK)) // Retorna HTTP 200
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Retorna HTTP 404 si no existe
    }

    // 4. Actualizar un Alumno (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizarAlumno(@PathVariable Long id, @RequestBody Alumno alumnoDetalles) {
        try {
            Alumno alumnoActualizado = alumnoService.actualizarAlumno(id, alumnoDetalles);
            return new ResponseEntity<>(alumnoActualizado, HttpStatus.OK); // Retorna HTTP 200
        } catch (IllegalArgumentException e) {
            // Manejo simplificado del error de "no encontrado" del servicio
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna HTTP 404
        }
    }

    // 5. Eliminar un Alumno (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna HTTP 204
    public void eliminarAlumno(@PathVariable Long id) {
        // Delegamos el manejo de excepciones de negocio al servicio, aunque
        // en una API real se usaría @ControllerAdvice para manejar errores globalmente.
        alumnoService.eliminarAlumno(id);
    }
}