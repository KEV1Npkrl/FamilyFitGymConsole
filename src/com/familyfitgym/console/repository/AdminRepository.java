package com.familyfitgym.console.repository;

import com.familyfitgym.console.model.Admin;
import java.util.List;
import java.util.Optional;

public interface AdminRepository {
    Optional<Admin> findByUsername(String username);
    Admin save(Admin admin);
    void deleteByUsername(String username);
    List<Admin> findAll();
}