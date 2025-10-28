package com.familyfitgym.console.model;

import java.time.LocalDate;

/**
 * Representa la asignación de una membresía a un usuario/socio
 */
public class UsuarioMembresia {
    private String id;
    private String usuarioDocumento; // Documento del usuario
    private String membresiaId; // ID de la membresía
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private boolean activo;
    private String estado; // ACTIVA, VENCIDA, SUSPENDIDA, CANCELADA

    public UsuarioMembresia() {
        this.activo = true;
        this.estado = "ACTIVA";
    }

    public UsuarioMembresia(String id, String usuarioDocumento, String membresiaId, 
                           LocalDate fechaInicio, LocalDate fechaVencimiento) {
        this.id = id;
        this.usuarioDocumento = usuarioDocumento;
        this.membresiaId = membresiaId;
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

    public String getUsuarioDocumento() {
        return usuarioDocumento;
    }

    public void setUsuarioDocumento(String usuarioDocumento) {
        this.usuarioDocumento = usuarioDocumento;
    }

    public String getMembresiaId() {
        return membresiaId;
    }

    public void setMembresiaId(String membresiaId) {
        this.membresiaId = membresiaId;
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
                ", usuarioDocumento='" + usuarioDocumento + '\'' +
                ", membresiaId='" + membresiaId + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaVencimiento=" + fechaVencimiento +
                ", estado='" + estado + '\'' +
                '}';
    }
}
