// filepath: FamilyFitGymConsole/src/com/familyfitgym/console/model/Admin.java
package com.familyfitgym.console.model;

import com.familyfitgym.console.util.PasswordUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Admin {
    private String numDocumento;
    private String tipoDocumento;
    private String nombres;
    private String apellidos;
    private String passwordHash;
    private String celular;
    private String correo;
    private String direccion;
    private LocalDate fechaNacimiento;
    private TipoEmpleado tipoEmpleado;
    private BigDecimal salario;
    private LocalDate fechaContratacion;
    private LocalDate fechaRegistro;

    public Admin(String numDocumento, String nombres, String apellidos, String password, TipoEmpleado tipoEmpleado) {
        this.numDocumento = numDocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.passwordHash = PasswordUtil.hashPassword(password);
        this.tipoEmpleado = tipoEmpleado;
        this.fechaRegistro = LocalDate.now();
    }

    // Getters y Setters
    public String getNumDocumento() { return numDocumento; }
    public void setNumDocumento(String numDocumento) { this.numDocumento = numDocumento; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public TipoEmpleado getTipoEmpleado() { return tipoEmpleado; }
    public void setTipoEmpleado(TipoEmpleado tipoEmpleado) { this.tipoEmpleado = tipoEmpleado; }

    public BigDecimal getSalario() { return salario; }
    public void setSalario(BigDecimal salario) { this.salario = salario; }

    public LocalDate getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(LocalDate fechaContratacion) { this.fechaContratacion = fechaContratacion; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}