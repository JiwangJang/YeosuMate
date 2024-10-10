package com.practice.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.model.User;
import com.practice.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    public ResponseEntity<String> register(@RequestBody User model) {
        System.out.println(model);
        // try {
        // userRepo.insertUser(user);
        // return true;
        // } catch (Exception e) {
        // // TODO: handle exception
        // return false;
        // }
        return ResponseEntity.status(HttpStatus.OK).body("null");
    }

}
