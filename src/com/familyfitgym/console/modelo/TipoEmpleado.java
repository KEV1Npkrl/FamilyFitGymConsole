package com.familyfitgym.console.modelo;

public enum TipoEmpleado {
    PROPIETARIO("Propietario"),
    CAJERO_RECEPCIONISTA("Cajero/Recepcionista"),
    INSTRUCTOR("Instructor");

    private final String nombreMostrar;

    TipoEmpleado(String nombreMostrar) {
        this.nombreMostrar = nombreMostrar;
    }

    public String getNombreMostrar() {
        return nombreMostrar;
    }
}
