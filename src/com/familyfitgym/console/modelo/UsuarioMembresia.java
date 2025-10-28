package com.familyfitgym.console.modelo;

import java.time.LocalDate;

/**
 * Representa la asignación de una membresía a un usuario/socio
 */
public class UsuarioMembresia {
    private String id;
    private String documentoUsuario; // Documento del usuario
    private String idMembresia; // ID de la membresía
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private boolean activo;
    private String estado; // ACTIVA, VENCIDA, SUSPENDIDA, CANCELADA

    public UsuarioMembresia() {
        this.activo = true;
        this.estado = "ACTIVA";
    }

    public UsuarioMembresia(String id, String documentoUsuario, String idMembresia, 
                           LocalDate fechaInicio, LocalDate fechaVencimiento) {
        this.id = id;
        this.documentoUsuario = documentoUsuario;
        this.idMembresia = idMembresia;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
        this.activo = true;
        this.estado = "ACTIVA";
    }

    public boolean estaActiva() {
        return activo && "ACTIVA".equals(estado) && 
               LocalDate.now().isBefore(fechaVencimiento.plusDays(1));
    }

    public boolean estaVencida() {
        return LocalDate.now().isAfter(fechaVencimiento);
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    public String getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(String idMembresia) {
        this.idMembresia = idMembresia;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "UsuarioMembresia{" +
                "id='" + id + '\'' +
                ", documentoUsuario='" + documentoUsuario + '\'' +
                ", idMembresia='" + idMembresia + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaVencimiento=" + fechaVencimiento +
                ", estado='" + estado + '\'' +
                '}';
    }
}
