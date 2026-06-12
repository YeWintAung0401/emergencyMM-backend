package com.maskx.emergencymm.api.controller;

import com.maskx.emergencymm.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<HashMap<String, Object>> register(@RequestBody HashMap<String, Object> data) {
        try {
            HashMap<String, Object> result = authService.register(data);

            if ("error".equals(result.get("status"))) {
                return ResponseEntity.badRequest().body(result);
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            HashMap<String, Object> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
