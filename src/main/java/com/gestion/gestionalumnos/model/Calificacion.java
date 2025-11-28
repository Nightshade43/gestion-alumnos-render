package com.gestion.gestionalumnos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Representa una nota individual obtenida por el alumno.
 */
@Entity
@Table(name = "calificaciones")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombrePrueba;

    // Usar BigDecimal para precisión en valores monetarios o notas.
    private BigDecimal valor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Argentina/Cordoba")
    private Instant fechaEvaluacion = Instant.now();

    @JsonIgnore
    // Relación Muchos-a-Uno: Muchas Calificaciones pertenecen a un solo Núcleo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nucleo_id", nullable = false)
    private NucleoCalificacion nucleo;

    public Calificacion() {
    }

    public Calificacion(String nombrePrueba, BigDecimal valor, NucleoCalificacion nucleo) {
        this.nombrePrueba = nombrePrueba;
        this.valor = valor;
        this.nucleo = nucleo;
    }

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePrueba() {
        return nombrePrueba;
    }

    public void setNombrePrueba(String nombrePrueba) {
        this.nombrePrueba = nombrePrueba;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Instant getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(Instant fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public NucleoCalificacion getNucleo() {
        return nucleo;
    }

    public void setNucleo(NucleoCalificacion nucleo) {
        this.nucleo = nucleo;
    }
}