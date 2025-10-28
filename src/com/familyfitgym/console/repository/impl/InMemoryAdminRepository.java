package com.familyfitgym.console.repository.impl;

import com.familyfitgym.console.model.Admin;
import com.familyfitgym.console.model.TipoEmpleado;
import com.familyfitgym.console.repository.AdminRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryAdminRepository implements AdminRepository {
    
    private final Map<String, Admin> admins = new ConcurrentHashMap<>();

    public InMemoryAdminRepository() {
        // Datos de prueba
        Admin propietario = new Admin("45678912", "Carlos", "Mendoza", "propietario123", TipoEmpleado.PROPIETARIO);
        propietario.setTipoDocumento("DNI");
        propietario.setFechaNacimiento(LocalDate.of(1980, 3, 15));
        propietario.setCelular("999888777");
        propietario.setCorreo("carlos.mendoza@familyfitgym.com");
        propietario.setDireccion("Av. Principal 789");
        propietario.setSalario(new java.math.BigDecimal("5000.00"));
        propietario.setFechaContratacion(LocalDate.of(2020, 1, 1));
        admins.put(propietario.getNumDocumento(), propietario);

        Admin cajero = new Admin("78945612", "Ana", "López", "cajero123", TipoEmpleado.CAJERO_RECEPCIONISTA);
        cajero.setTipoDocumento("DNI");
        cajero.setFechaNacimiento(LocalDate.of(1992, 7, 22));
        cajero.setCelular("988777666");
        cajero.setCorreo("ana.lopez@familyfitgym.com");
        cajero.setDireccion("Jr. Comercio 321");
        cajero.setSalario(new java.math.BigDecimal("1800.00"));
        cajero.setFechaContratacion(LocalDate.of(2021, 3, 15));
        admins.put(cajero.getNumDocumento(), cajero);

        Admin instructor = new Admin("32165498", "Luis", "Ramírez", "instructor123", TipoEmpleado.INSTRUCTOR);
        instructor.setTipoDocumento("DNI");
        instructor.setFechaNacimiento(LocalDate.of(1988, 11, 5));
        instructor.setCelular("977666555");
        instructor.setCorreo("luis.ramirez@familyfitgym.com");
        instructor.setDireccion("Av. Deportes 654");
        instructor.setSalario(new java.math.BigDecimal("2200.00"));
        instructor.setFechaContratacion(LocalDate.of(2021, 6, 1));
        admins.put(instructor.getNumDocumento(), instructor);
    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        return Optional.ofNullable(admins.get(username));
    }

    @Override
    public Admin save(Admin admin) {
        admins.put(admin.getNumDocumento(), admin);
        return admin;
    }

    @Override
    public void deleteByUsername(String username) {
        admins.remove(username);
    }

    @Override
    public List<Admin> findAll() {
        return new ArrayList<>(admins.values());
    }
}