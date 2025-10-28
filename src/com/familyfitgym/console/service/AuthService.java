package com.familyfitgym.console.service;

import com.familyfitgym.console.model.Admin;
import com.familyfitgym.console.model.User;
import com.familyfitgym.console.repository.AdminRepository;
import com.familyfitgym.console.repository.UserRepository;
import com.familyfitgym.console.util.PasswordUtil;

import java.util.Optional;

public class AuthService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private User currentUser;
    private Admin currentAdmin;

    public AuthService(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    public boolean loginUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (PasswordUtil.checkPassword(password, user.getPasswordHash())) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public boolean loginAdmin(String username, String password) {
        Optional<Admin> adminOpt = adminRepository.findByUsername(username);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (PasswordUtil.checkPassword(password, admin.getPasswordHash())) {
                currentAdmin = admin;
                return true;
            }
        }
        return false;
    }

    public boolean verifyPassword(String username, String password) {
        Optional<Admin> adminOpt = adminRepository.findByUsername(username);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            return PasswordUtil.checkPassword(password, admin.getPasswordHash());
        }
        return false;
    }

    public void logout() {
        currentUser = null;
        currentAdmin = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public AdminRepository getAdminRepository() {
        return adminRepository;
    }
}