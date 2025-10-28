package com.familyfitgym.console.repositorio;

import com.familyfitgym.console.modelo.Membresia;
import java.util.List;
import java.util.Optional;

public interface RepositorioMembresia {
    void guardar(Membresia membresia);
    Optional<Membresia> buscarPorId(String id);
    List<Membresia> buscarTodas();
    List<Membresia> buscarTodasActivas();
    void eliminarPorId(String id);
}
