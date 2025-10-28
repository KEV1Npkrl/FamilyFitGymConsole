package com.familyfitgym.console.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa un plan de membresía del gimnasio
 */
public class Membresia {
    private String id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int duracionDias; // Duración en días
    private boolean activo;
    private LocalDate fechaCreacion;

    public Membresia() {
        this.fechaCreacion = LocalDate.now();
        this.activo = true;
    }

    public Membresia(String id, String nombre, String descripcion, BigDecimal precio, int duracionDias) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionDias = duracionDias;
        this.activo = true;
        this.fechaCreacion = LocalDate.now();
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Membresia{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", duracionDias=" + duracionDias +
                ", activo=" + activo +
                '}';
    }
}
