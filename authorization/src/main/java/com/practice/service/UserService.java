package com.practice.service;

import org.springframework.stereotype.Service;

import com.practice.model.User;
import com.practice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.ArrayList;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach((user) -> {
            log.info((String) user.get("email"));
        });
        return users;
    }
}
