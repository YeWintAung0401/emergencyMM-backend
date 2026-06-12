package com.maskx.emergencymm.service;

import com.maskx.emergencymm.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public HashMap<String, Object> getUser(HashMap<String, Object> data) throws Exception {
        HashMap<String, Object> set = new HashMap<String, Object>();
        try {
            set = userRepository.getUser(data);
        } finally {
//            ServerUtil.closeConnection(conn);
        }
        return set;
    }
}
