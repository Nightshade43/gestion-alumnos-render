package com.gestion.gestionalumnos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de Asociación: Registra la inscripción de un Alumno a un Curso.
 */
@Entity
@Table(name = "matriculas")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación Muchos-a-Uno: Muchas matrículas apuntan a un solo Alumno
    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false) // Columna de clave foránea
    private Alumno alumno;

    // Relación Muchos-a-Uno: Muchas matrículas apuntan a un solo Curso
    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false) // Columna de clave foránea
    private Curso curso;

    @JsonIgnore // <--- ¡Añadir esta línea!
    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NucleoCalificacion> nucleos = new ArrayList<>();

    // Nota: Las relaciones a NucleoCalificacion se añadirán aquí en el siguiente paso.

    public Matricula() {
    }

    public Matricula(Alumno alumno, Curso curso) {
        this.alumno = alumno;
        this.curso = curso;
    }

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}