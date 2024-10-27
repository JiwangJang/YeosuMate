package com.yeosu_mate.backend.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.yeosu_mate.backend.model.dto.RegisterUserDto;
import com.yeosu_mate.backend.model.entity.User;
import com.yeosu_mate.backend.model.process.ProcessResult;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Repository
@Slf4j
public class UserRepository implements UserDetailsService {
    private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT * FROM users WHERE id=?";

        return jdbcTemplate.queryForObject(sql, (row, index) -> {
            User emptyUser = new User();
            emptyUser.setId(row.getString("id"));
            emptyUser.setPassword(row.getString("password"));
            emptyUser.setNickname(row.getString("nickname"));
            return emptyUser;
        }, username);
    }

    public ProcessResult registerUser(RegisterUserDto registerUserDto) {
        try {
            String sql = "INSERT INTO users(id, password, nickname) VALUES(?, ?, ?)";
            jdbcTemplate.update(sql, registerUserDto.getId(), registerUserDto.getPassword(),
                    registerUserDto.getNickname());
            return new ProcessResult(true, "Register completed!");
        } catch (Exception e) {
            log.info("*****Error Object : {}", e);
            return new ProcessResult(false, "Something is wrong!");
        }
    }

    public Optional<List<User>> getAllUsers() {
        String sql = "SELECT * FROM users;";
        try {
            List<User> result = jdbcTemplate.queryForList(sql, User.class);
            return Optional.ofNullable(result);
        } catch (Exception e) {
            log.info("result : {}", e);
            return Optional.ofNullable(null);
        }

    }
}
