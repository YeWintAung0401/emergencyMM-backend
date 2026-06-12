package com.maskx.emergencymm.service;

import com.maskx.emergencymm.api.model.User;
import com.maskx.emergencymm.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Integer userId) {
        return userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }
}
