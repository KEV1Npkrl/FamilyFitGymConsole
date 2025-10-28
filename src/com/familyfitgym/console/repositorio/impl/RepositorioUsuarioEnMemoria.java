package com.familyfitgym.console.repositorio.impl;

import com.familyfitgym.console.modelo.Usuario;
import com.familyfitgym.console.repositorio.RepositorioUsuario;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RepositorioUsuarioEnMemoria implements RepositorioUsuario {
    
    private final Map<String, Usuario> usuarios = new ConcurrentHashMap<>();

    public RepositorioUsuarioEnMemoria() {
        // Datos de prueba
        Usuario juan = new Usuario("12345678", "Juan", "Pérez", "user123");
        juan.setTipoDocumento("DNI");
        juan.setFechaNacimiento(LocalDate.of(1995, 5, 10));
        juan.setGenero("Masculino");
        juan.setCelular("987654321");
        juan.setCorreo("juan.perez@example.com");
        juan.setDireccion("Av. Lima 123");
        usuarios.put(juan.getNumDocumento(), juan);

        Usuario maria = new Usuario("87654321", "María", "García", "user123");
        maria.setTipoDocumento("DNI");
        maria.setFechaNacimiento(LocalDate.of(1998, 8, 20));
        maria.setGenero("Femenino");
        maria.setCelular("912345678");
        maria.setCorreo("maria.garcia@example.com");
        maria.setDireccion("Jr. Puno 456");
        usuarios.put(maria.getNumDocumento(), maria);
    }

    @Override
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return Optional.ofNullable(usuarios.get(nombreUsuario));
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        usuarios.put(usuario.getNumDocumento(), usuario);
        return usuario;
    }

    @Override
    public void eliminarPorNombreUsuario(String nombreUsuario) {
        usuarios.remove(nombreUsuario);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return new ArrayList<>(usuarios.values());
    }
}
