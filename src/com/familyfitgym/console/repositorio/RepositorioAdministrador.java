package com.familyfitgym.console.repositorio;

import com.familyfitgym.console.modelo.Administrador;
import java.util.List;
import java.util.Optional;

public interface RepositorioAdministrador {
    Optional<Administrador> buscarPorNombreUsuario(String nombreUsuario);
    Administrador guardar(Administrador administrador);
    void eliminarPorNombreUsuario(String nombreUsuario);
    List<Administrador> buscarTodos();
}
