package com.gestion.gestionalumnos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Componente que intercepta y maneja excepciones lanzadas por los Controladores
 * a nivel global, mapeándolas a códigos de estado HTTP correctos.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Manejo de Conflictos de Negocio (HTTP 409)
    // Ejemplo: Email ya registrado, Alumno ya matriculado.
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).substring(4)); // Obtiene la ruta limpia

        // Retorna el objeto body con código HTTP 409
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    // 2. Manejo de Recursos No Encontrados o Datos Inválidos (HTTP 404 / 400)
    // Ejemplo: Alumno/Curso con ID no existente, Datos faltantes.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).substring(4));

        // Retorna el objeto body con código HTTP 404
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}