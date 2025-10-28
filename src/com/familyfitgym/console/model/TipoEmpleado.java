package com.familyfitgym.console.model;

public enum TipoEmpleado {
    PROPIETARIO("Propietario"),
    CAJERO_RECEPCIONISTA("Cajero/Recepcionista"),
    INSTRUCTOR("Instructor");

    private final String displayName;

    TipoEmpleado(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}