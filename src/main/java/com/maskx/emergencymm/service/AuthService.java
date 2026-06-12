package com.maskx.emergencymm.service;

import com.maskx.emergencymm.api.repository.AuthRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {

    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public HashMap<String, Object> register(HashMap<String, Object> data) throws Exception {
        HashMap<String, Object> result = new HashMap<>();

        // ── Validate required fields ─────────────────────────────────────
        if (data.get("username") == null || data.get("username").toString().isEmpty()) {
            result.put("status", "error");
            result.put("message", "Username is required");
            return result;
        }

        if (data.get("email") == null || data.get("email").toString().isEmpty()) {
            result.put("status", "error");
            result.put("message", "Email is required");
            return result;
        }

        if (data.get("password") == null || data.get("password").toString().isEmpty()) {
            result.put("status", "error");
            result.put("message", "Password is required");
            return result;
        }

        // ── Check if email already exists ─────────────────────────────────
        if (authRepository.emailExists(data.get("email").toString())) {
            result.put("status", "error");
            result.put("message", "Email already exists");
            return result;
        }

        // ── Hash password using jBCrypt ───────────────────────────────────
        String hashedPassword = BCrypt.hashpw(data.get("password").toString(), BCrypt.gensalt());
        data.put("password", hashedPassword);

        // ── Insert user ───────────────────────────────────────────────────
        int rows = authRepository.register(data);

        if (rows > 0) {
            result.put("status", "success");
            result.put("message", "User registered successfully");
        } else {
            result.put("status", "error");
            result.put("message", "Registration failed");
        }

        return result;
    }

}
