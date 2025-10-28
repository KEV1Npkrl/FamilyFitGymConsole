package com.familyfitgym.console.repository.impl;

import com.familyfitgym.console.model.UsuarioMembresia;
import com.familyfitgym.console.repository.UsuarioMembresiaRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryUsuarioMembresiaRepository implements UsuarioMembresiaRepository {

    private final Map<String, UsuarioMembresia> usuarioMembresias;

    public InMemoryUsuarioMembresiaRepository() {
        this.usuarioMembresias = new HashMap<>();
    }

    @Override
    public void save(UsuarioMembresia usuarioMembresia) {
        usuarioMembresias.put(usuarioMembresia.getId(), usuarioMembresia);
    }

    @Override
    public Optional<UsuarioMembresia> findById(String id) {
        return Optional.ofNullable(usuarioMembresias.get(id));
    }

    @Override
    public Optional<UsuarioMembresia> findActivaByUsuarioDocumento(String usuarioDocumento) {
        return usuarioMembresias.values().stream()
                .filter(um -> um.getUsuarioDocumento().equals(usuarioDocumento))
                .filter(UsuarioMembresia::estaActiva)
                .findFirst();
    }

    @Override
    public List<UsuarioMembresia> findByUsuarioDocumento(String usuarioDocumento) {
        return usuarioMembresias.values().stream()
                .filter(um -> um.getUsuarioDocumento().equals(usuarioDocumento))
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioMembresia> findAll() {
        return new ArrayList<>(usuarioMembresias.values());
    }

    @Override
    public void deleteById(String id) {
        usuarioMembresias.remove(id);
    }
}
