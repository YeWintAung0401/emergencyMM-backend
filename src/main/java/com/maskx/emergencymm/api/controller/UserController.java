package com.maskx.emergencymm.api.controller;

import com.maskx.emergencymm.api.model.User;
import com.maskx.emergencymm.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/users")

public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/getUsers")
    public ResponseEntity<HashMap<String, Object>> getUser(@RequestBody HashMap<String, Object> data) {
        try {
            HashMap<String, Object> result = userService.getUser(data);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            HashMap<String, Object> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
