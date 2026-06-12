package com.maskx.emergencymm.api.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class AuthRepository {
    private final JdbcTemplate jdbcTemplate;

    public AuthRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ── Check if email already exists ────────────────────────────────────
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM Users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    // ── Register new user ────────────────────────────────────────────────
    public int register(HashMap<String, Object> data) {
        String sql = "INSERT INTO Users (username, email, password, phone, age) VALUES (?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
                data.get("username"),
                data.get("email"),
                data.get("password"),   // already hashed from service
                data.get("phone"),
                data.get("age")
        );
    }
}
