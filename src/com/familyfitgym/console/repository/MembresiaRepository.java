package com.familyfitgym.console.repository;

import com.familyfitgym.console.model.Membresia;
import java.util.List;
import java.util.Optional;

public interface MembresiaRepository {
    void save(Membresia membresia);
    Optional<Membresia> findById(String id);
    List<Membresia> findAll();
    List<Membresia> findAllActivas();
    void deleteById(String id);
}
