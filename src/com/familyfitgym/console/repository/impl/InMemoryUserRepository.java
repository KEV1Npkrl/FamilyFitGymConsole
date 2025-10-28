package com.familyfitgym.console.repository.impl;

import com.familyfitgym.console.model.User;
import com.familyfitgym.console.repository.UserRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {
    
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public InMemoryUserRepository() {
        // Datos de prueba
        User juan = new User("12345678", "Juan", "Pérez", "user123");
        juan.setTipoDocumento("DNI");
        juan.setFechaNacimiento(LocalDate.of(1995, 5, 10));
        juan.setGenero("Masculino");
        juan.setCelular("987654321");
        juan.setCorreo("juan.perez@example.com");
        juan.setDireccion("Av. Lima 123");
        users.put(juan.getNumDocumento(), juan);

        User maria = new User("87654321", "María", "García", "user123");
        maria.setTipoDocumento("DNI");
        maria.setFechaNacimiento(LocalDate.of(1998, 8, 20));
        maria.setGenero("Femenino");
        maria.setCelular("912345678");
        maria.setCorreo("maria.garcia@example.com");
        maria.setDireccion("Jr. Puno 456");
        users.put(maria.getNumDocumento(), maria);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    @Override
    public User save(User user) {
        users.put(user.getNumDocumento(), user);
        return user;
    }

    @Override
    public void deleteByUsername(String username) {
        users.remove(username);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}