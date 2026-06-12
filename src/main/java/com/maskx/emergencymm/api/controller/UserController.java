package com.maskx.emergencymm.api.controller;

import com.maskx.emergencymm.api.model.User;
import com.maskx.emergencymm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user")
    public User getUser(@RequestParam Integer userId){
        return userService.getUser(userId);
    }
}
