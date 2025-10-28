package com.familyfitgym.console.servicio;

import com.familyfitgym.console.modelo.Administrador;
import com.familyfitgym.console.modelo.Usuario;
import com.familyfitgym.console.repositorio.RepositorioAdministrador;
import com.familyfitgym.console.repositorio.RepositorioUsuario;
import com.familyfitgym.console.utilidad.UtilidadPassword;

import java.util.Optional;

public class ServicioAutenticacion {
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioAdministrador repositorioAdministrador;
    private Usuario usuarioActual;
    private Administrador administradorActual;

    public ServicioAutenticacion(RepositorioUsuario repositorioUsuario, RepositorioAdministrador repositorioAdministrador) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioAdministrador = repositorioAdministrador;
    }

    public boolean iniciarSesionUsuario(String nombreUsuario, String password) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.buscarPorNombreUsuario(nombreUsuario);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (UtilidadPassword.verificarPassword(password, usuario.getHashPassword())) {
                usuarioActual = usuario;
                return true;
            }
        }
        return false;
    }

    public boolean iniciarSesionAdministrador(String nombreUsuario, String password) {
        Optional<Administrador> administradorOpt = repositorioAdministrador.buscarPorNombreUsuario(nombreUsuario);
        if (administradorOpt.isPresent()) {
            Administrador administrador = administradorOpt.get();
            if (UtilidadPassword.verificarPassword(password, administrador.getHashPassword())) {
                administradorActual = administrador;
                return true;
            }
        }
        return false;
    }

    public boolean verificarPassword(String nombreUsuario, String password) {
        Optional<Administrador> administradorOpt = repositorioAdministrador.buscarPorNombreUsuario(nombreUsuario);
        if (administradorOpt.isPresent()) {
            Administrador administrador = administradorOpt.get();
            return UtilidadPassword.verificarPassword(password, administrador.getHashPassword());
        }
        return false;
    }

    public void cerrarSesion() {
        usuarioActual = null;
        administradorActual = null;
    }

    public Usuario obtenerUsuarioActual() {
        return usuarioActual;
    }

    public Administrador obtenerAdministradorActual() {
        return administradorActual;
    }

    public RepositorioUsuario obtenerRepositorioUsuario() {
        return repositorioUsuario;
    }

    public RepositorioAdministrador obtenerRepositorioAdministrador() {
        return repositorioAdministrador;
    }
}
