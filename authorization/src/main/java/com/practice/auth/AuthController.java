package com.practice.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.model.User;
import com.practice.repository.UserRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/api", produces = "application/json")
public class AuthController {
    private UserRepository userRepo;

    public AuthController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping(path = "/register")
    public boolean register(@RequestBody User user) {
        try {
            userRepo.insertUser(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
