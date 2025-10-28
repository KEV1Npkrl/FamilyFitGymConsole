package com.familyfitgym.console.repositorio.impl;

import com.familyfitgym.console.modelo.Administrador;
import com.familyfitgym.console.modelo.TipoEmpleado;
import com.familyfitgym.console.repositorio.RepositorioAdministrador;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RepositorioAdministradorEnMemoria implements RepositorioAdministrador {
    
    private final Map<String, Administrador> administradores = new ConcurrentHashMap<>();

    public RepositorioAdministradorEnMemoria() {
        // Datos de prueba
        Administrador propietario = new Administrador("45678912", "Carlos", "Mendoza", "propietario123", TipoEmpleado.PROPIETARIO);
        propietario.setTipoDocumento("DNI");
        propietario.setFechaNacimiento(LocalDate.of(1980, 3, 15));
        propietario.setCelular("999888777");
        propietario.setCorreo("carlos.mendoza@familyfitgym.com");
        propietario.setDireccion("Av. Principal 789");
        propietario.setSalario(new java.math.BigDecimal("5000.00"));
        propietario.setFechaContratacion(LocalDate.of(2020, 1, 1));
        administradores.put(propietario.getNumDocumento(), propietario);

        Administrador cajero = new Administrador("78945612", "Ana", "López", "cajero123", TipoEmpleado.CAJERO_RECEPCIONISTA);
        cajero.setTipoDocumento("DNI");
        cajero.setFechaNacimiento(LocalDate.of(1992, 7, 22));
        cajero.setCelular("988777666");
        cajero.setCorreo("ana.lopez@familyfitgym.com");
        cajero.setDireccion("Jr. Comercio 321");
        cajero.setSalario(new java.math.BigDecimal("1800.00"));
        cajero.setFechaContratacion(LocalDate.of(2021, 3, 15));
        administradores.put(cajero.getNumDocumento(), cajero);

        Administrador instructor = new Administrador("32165498", "Luis", "Ramírez", "instructor123", TipoEmpleado.INSTRUCTOR);
        instructor.setTipoDocumento("DNI");
        instructor.setFechaNacimiento(LocalDate.of(1988, 11, 5));
        instructor.setCelular("977666555");
        instructor.setCorreo("luis.ramirez@familyfitgym.com");
        instructor.setDireccion("Av. Deportes 654");
        instructor.setSalario(new java.math.BigDecimal("2200.00"));
        instructor.setFechaContratacion(LocalDate.of(2021, 6, 1));
        administradores.put(instructor.getNumDocumento(), instructor);
    }

    @Override
    public Optional<Administrador> buscarPorNombreUsuario(String nombreUsuario) {
        return Optional.ofNullable(administradores.get(nombreUsuario));
    }

    @Override
    public Administrador guardar(Administrador administrador) {
        administradores.put(administrador.getNumDocumento(), administrador);
        return administrador;
    }

    @Override
    public void eliminarPorNombreUsuario(String nombreUsuario) {
        administradores.remove(nombreUsuario);
    }

    @Override
    public List<Administrador> buscarTodos() {
        return new ArrayList<>(administradores.values());
    }
}
