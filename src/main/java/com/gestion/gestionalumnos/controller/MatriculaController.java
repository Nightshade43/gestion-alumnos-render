package com.gestion.gestionalumnos.controller;

import com.gestion.gestionalumnos.model.Matricula;
import com.gestion.gestionalumnos.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/matriculas")
@CrossOrigin(origins = "*")
public class MatriculaController {

    private final MatriculaService matriculaService;

    @Autowired
    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    // 1. Crear una Matrícula (Inscribir Alumno a Curso) (POST)
    // Espera un cuerpo JSON con los IDs: {"alumnoId": 1, "cursoId": 2}
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // HTTP 201
    public Matricula matricularAlumno(@RequestBody Map<String, Long> request) {
        // Extraemos los IDs del mapa de entrada
        Long alumnoId = request.get("alumnoId");
        Long cursoId = request.get("cursoId");

        if (alumnoId == null || cursoId == null) {
            throw new IllegalArgumentException("Se requieren 'alumnoId' y 'cursoId'.");
        }

        // El servicio maneja la validación de existencia y doble inscripción
        return matriculaService.matricularAlumno(alumnoId, cursoId);
    }

    // 2. Obtener una Matrícula específica por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Matricula> obtenerPorId(@PathVariable Long id) {
        return matriculaService.obtenerPorId(id)
                .map(matricula -> new ResponseEntity<>(matricula, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 3. Obtener todas las matrículas de un Alumno (GET)
    @GetMapping("/alumno/{alumnoId}")
    public List<Matricula> obtenerMatriculasPorAlumno(@PathVariable Long alumnoId) {
        return matriculaService.obtenerMatriculasPorAlumno(alumnoId);
    }

    // 4. Eliminar una Matrícula (Desmatricular) (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // HTTP 204
    public void desmatricularAlumno(@PathVariable Long id) {
        matriculaService.desmatricularAlumno(id);
    }

    // 5. Obtener Nota Final del Curso (GET)
    @GetMapping("/{id}/nota-final")
    public BigDecimal obtenerNotaFinal(@PathVariable Long id) {
        // Delega la lógica de cálculo al servicio
        return matriculaService.calcularNotaFinal(id);
    }
}