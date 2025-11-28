package com.gestion.gestionalumnos.controller;

import com.gestion.gestionalumnos.model.Curso;
import com.gestion.gestionalumnos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoService cursoService;

    @Autowired
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    // 1. Crear un nuevo Curso (POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // HTTP 201
    public Curso crearCurso(@RequestBody Curso curso) {
        return cursoService.crearCurso(curso);
    }

    // 2. Obtener todos los Cursos (GET)
    @GetMapping
    public List<Curso> obtenerTodos() {
        return cursoService.obtenerTodos();
    }

    // 3. Obtener un Curso por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerPorId(@PathVariable Long id) {
        return cursoService.obtenerPorId(id)
                .map(curso -> new ResponseEntity<>(curso, HttpStatus.OK)) // HTTP 200
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // HTTP 404
    }

    // 4. Actualizar un Curso (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Curso> actualizarCurso(@PathVariable Long id, @RequestBody Curso cursoDetalles) {
        try {
            Curso cursoActualizado = cursoService.actualizarCurso(id, cursoDetalles);
            return new ResponseEntity<>(cursoActualizado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Eliminar un Curso (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // HTTP 204
    public void eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
    }
}