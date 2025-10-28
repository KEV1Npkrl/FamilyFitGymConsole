package com.familyfitgym.console.repository;

import com.familyfitgym.console.model.UsuarioMembresia;
import java.util.List;
import java.util.Optional;

public interface UsuarioMembresiaRepository {
    void save(UsuarioMembresia usuarioMembresia);
    Optional<UsuarioMembresia> findById(String id);
    Optional<UsuarioMembresia> findActivaByUsuarioDocumento(String usuarioDocumento);
    List<UsuarioMembresia> findByUsuarioDocumento(String usuarioDocumento);
    List<UsuarioMembresia> findAll();
    void deleteById(String id);
}
