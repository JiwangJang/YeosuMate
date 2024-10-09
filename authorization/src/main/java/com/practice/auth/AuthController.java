package com.practice.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.model.User;
import com.practice.repository.UserRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "/api", produces = "application/json")
public class AuthController {
    private UserRepository userRepo;

    public AuthController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping(path = "/register", consumes = "application/json")
    public void register(@RequestBody User user) {
        System.out.println(user);
        // try {
        // userRepo.insertUser(user);
        // return true;
        // } catch (Exception e) {
        // // TODO: handle exception
        // return false;
        // }
    }

}
