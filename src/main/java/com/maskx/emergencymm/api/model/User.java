package com.maskx.emergencymm.api.model;

public class User {


    private Long userId;
    private String username;
    private String email;
    private String password;
    private int age;
    private String phone;

    // ── Constructor ──────────────────────────────
    public User(Long userId, String username, String email, String password, int age, String phone) {
        this.userId   = userId;
        this.username = username;
        this.email    = email;
        this.password = password;
        this.age      = age;
        this.phone    = phone;
    }

    // ── Getters ──────────────────────────────────
    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword()   { return password; }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    // ── Setters ──────────────────────────────────
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) { this.password = password; }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



}
