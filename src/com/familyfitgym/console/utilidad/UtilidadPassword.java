package com.familyfitgym.console.utilidad;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UtilidadPassword {
    
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear password", e);
        }
    }
    
    public static boolean verificarPassword(String password, String hash) {
        return hashPassword(password).equals(hash);
    }
}
