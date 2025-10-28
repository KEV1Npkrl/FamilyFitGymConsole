package com.familyfitgym.console.repository.impl;

import com.familyfitgym.console.model.Membresia;
import com.familyfitgym.console.repository.MembresiaRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMembresiaRepository implements MembresiaRepository {

    private final Map<String, Membresia> membresias;

    public InMemoryMembresiaRepository() {
        this.membresias = new HashMap<>();
        // Inicializar con membresías por defecto
        save(new Membresia("MEM-001", "Mensual", "Acceso ilimitado por 30 días", 
                          new BigDecimal("150.00"), 30));
        save(new Membresia("MEM-002", "Trimestral", "Acceso ilimitado por 90 días", 
                          new BigDecimal("400.00"), 90));
        save(new Membresia("MEM-003", "Semestral", "Acceso ilimitado por 180 días", 
                          new BigDecimal("750.00"), 180));
        save(new Membresia("MEM-004", "Anual", "Acceso ilimitado por 365 días", 
                          new BigDecimal("1400.00"), 365));
    }

    @Override
    public void save(Membresia membresia) {
        membresias.put(membresia.getId(), membresia);
    }

    @Override
    public Optional<Membresia> findById(String id) {
        return Optional.ofNullable(membresias.get(id));
    }

    @Override
    public List<Membresia> findAll() {
        return new ArrayList<>(membresias.values());
    }

    @Override
    public List<Membresia> findAllActivas() {
        return membresias.values().stream()
                .filter(Membresia::isActivo)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        membresias.remove(id);
    }
}
