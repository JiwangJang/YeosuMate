package com.yeosu_mate.backend.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.yeosu_mate.backend.model.dto.RegisterUserDto;
import com.yeosu_mate.backend.model.entity.User;
import com.yeosu_mate.backend.model.process.ProcessResult;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@AllArgsConstructor
@Slf4j
public class UserRepository implements UserDetailsService {
    private JdbcTemplate jdbcTemplate;
    private PasswordEncoder passwordEncoder;

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
            String encodedPassword = passwordEncoder.encode(registerUserDto.getPassword());
            jdbcTemplate.update(sql, registerUserDto.getId(), encodedPassword, registerUserDto.getNickname());
            return new ProcessResult(true, "Register completed!");
        } catch (Exception e) {
            log.info("*****Error Object : {}", e);
            return new ProcessResult(false, "Something is wrong!");
        }
    }
}
