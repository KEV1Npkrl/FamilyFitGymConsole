package com.familyfitgym.console.repositorio;

import com.familyfitgym.console.modelo.Usuario;
import java.util.List;
import java.util.Optional;

public interface RepositorioUsuario {
    Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario);
    Usuario guardar(Usuario usuario);
    void eliminarPorNombreUsuario(String nombreUsuario);
    List<Usuario> buscarTodos();
}
