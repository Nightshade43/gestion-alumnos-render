package com.gestion.gestionalumnos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

/**
 * Agrupación lógica de calificaciones (e.g., "Parcial 1", "Proyecto").
 */
@Entity
@Table(name = "nucleos_calificacion")
public class NucleoCalificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreNucleo;

    // Ponderación del núcleo en la nota final del curso (opcional)
    private Double ponderacion;

    @JsonIgnore
    // Relación Muchos-a-Uno: Muchos Núcleos pertenecen a una sola Matrícula
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_id", nullable = false)
    private Matricula matricula;

    // Relación Uno-a-Muchos: Un Núcleo contiene muchas Calificaciones
    // CascadeType.ALL asegura que si borras el núcleo, se borran las calificaciones hijas.
    @OneToMany(mappedBy = "nucleo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calificacion> calificaciones;


    public NucleoCalificacion() {
    }

    public NucleoCalificacion(String nombreNucleo, Double ponderacion, Matricula matricula) {
        this.nombreNucleo = nombreNucleo;
        this.ponderacion = ponderacion;
        this.matricula = matricula;
    }

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreNucleo() {
        return nombreNucleo;
    }

    public void setNombreNucleo(String nombreNucleo) {
        this.nombreNucleo = nombreNucleo;
    }

    public Double getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(Double ponderacion) {
        this.ponderacion = ponderacion;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public List<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }
}