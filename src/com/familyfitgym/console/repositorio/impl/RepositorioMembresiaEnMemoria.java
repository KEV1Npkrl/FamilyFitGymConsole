package com.familyfitgym.console.repositorio.impl;

import com.familyfitgym.console.modelo.Membresia;
import com.familyfitgym.console.repositorio.RepositorioMembresia;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class RepositorioMembresiaEnMemoria implements RepositorioMembresia {

    private final Map<String, Membresia> membresias;

    public RepositorioMembresiaEnMemoria() {
        this.membresias = new HashMap<>();
        // Inicializar con membresías por defecto
        guardar(new Membresia("MEM-001", "Mensual", "Acceso ilimitado por 30 días", 
                          new BigDecimal("150.00"), 30));
        guardar(new Membresia("MEM-002", "Trimestral", "Acceso ilimitado por 90 días", 
                          new BigDecimal("400.00"), 90));
        guardar(new Membresia("MEM-003", "Semestral", "Acceso ilimitado por 180 días", 
                          new BigDecimal("750.00"), 180));
        guardar(new Membresia("MEM-004", "Anual", "Acceso ilimitado por 365 días", 
                          new BigDecimal("1400.00"), 365));
    }

    @Override
    public void guardar(Membresia membresia) {
        membresias.put(membresia.getId(), membresia);
    }

    @Override
    public Optional<Membresia> buscarPorId(String id) {
        return Optional.ofNullable(membresias.get(id));
    }

    @Override
    public List<Membresia> buscarTodas() {
        return new ArrayList<>(membresias.values());
    }

    @Override
    public List<Membresia> buscarTodasActivas() {
        return membresias.values().stream()
                .filter(Membresia::isActivo)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarPorId(String id) {
        membresias.remove(id);
    }
}
