package com.yeosu_mate.backend.controller.userInfo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yeosu_mate.backend.model.entity.User;
import com.yeosu_mate.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUser() {
        Optional<List<User>> users = userRepository.getAllUsers();
        
        return ResponseEntity.ok(users.get());
    }

}
