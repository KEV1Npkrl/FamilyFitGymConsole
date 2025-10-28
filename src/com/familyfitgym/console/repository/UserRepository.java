package com.familyfitgym.console.repository;

import com.familyfitgym.console.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    User save(User user);
    void deleteByUsername(String username);
    List<User> findAll();
}