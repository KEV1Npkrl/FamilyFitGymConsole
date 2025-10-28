package com.familyfitgym.console.repositorio.impl;

import com.familyfitgym.console.modelo.UsuarioMembresia;
import com.familyfitgym.console.repositorio.RepositorioUsuarioMembresia;

import java.util.*;
import java.util.stream.Collectors;

public class RepositorioUsuarioMembresiaEnMemoria implements RepositorioUsuarioMembresia {

    private final Map<String, UsuarioMembresia> usuariosMembresias;

    public RepositorioUsuarioMembresiaEnMemoria() {
        this.usuariosMembresias = new HashMap<>();
    }

    @Override
    public void guardar(UsuarioMembresia usuarioMembresia) {
        usuariosMembresias.put(usuarioMembresia.getId(), usuarioMembresia);
    }

    @Override
    public Optional<UsuarioMembresia> buscarPorId(String id) {
        return Optional.ofNullable(usuariosMembresias.get(id));
    }

    @Override
    public Optional<UsuarioMembresia> buscarActivaPorDocumentoUsuario(String documentoUsuario) {
        return usuariosMembresias.values().stream()
                .filter(um -> um.getDocumentoUsuario().equals(documentoUsuario))
                .filter(UsuarioMembresia::estaActiva)
                .findFirst();
    }

    @Override
    public List<UsuarioMembresia> buscarPorDocumentoUsuario(String documentoUsuario) {
        return usuariosMembresias.values().stream()
                .filter(um -> um.getDocumentoUsuario().equals(documentoUsuario))
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioMembresia> buscarTodas() {
        return new ArrayList<>(usuariosMembresias.values());
    }

    @Override
    public void eliminarPorId(String id) {
        usuariosMembresias.remove(id);
    }
}
