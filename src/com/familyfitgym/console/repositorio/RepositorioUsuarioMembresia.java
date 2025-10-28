package com.familyfitgym.console.repositorio;

import com.familyfitgym.console.modelo.UsuarioMembresia;
import java.util.List;
import java.util.Optional;

public interface RepositorioUsuarioMembresia {
    void guardar(UsuarioMembresia usuarioMembresia);
    Optional<UsuarioMembresia> buscarPorId(String id);
    Optional<UsuarioMembresia> buscarActivaPorDocumentoUsuario(String documentoUsuario);
    List<UsuarioMembresia> buscarPorDocumentoUsuario(String documentoUsuario);
    List<UsuarioMembresia> buscarTodas();
    void eliminarPorId(String id);
}
