package com.practice.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.practice.model.User;

import java.util.Optional;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Optional<User>> userMapper = (row, index) -> {
        User emptyUser = new User();
        emptyUser.setCreatedAt(row.getDate("createdAt"));
        emptyUser.setUpdatedAt(row.getDate("updatedAt"));
        emptyUser.setEmail(row.getString("email"));
        emptyUser.setFullName(row.getString("fullName"));
        emptyUser.setId(row.getInt("id"));
        emptyUser.setPassword(row.getString("password"));
        return Optional.of(emptyUser);
    };

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email=?";
        return jdbcTemplate.queryForObject(sql, userMapper, email);
    }

    public User save(User user) {
        String sql = "INSERT INTO users(fullName, email, password, createdAt, updatedAt) VALUES(?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, user.getFullName(), user.getEmail(), user.getPassword(), user.getCreatedAt(),
                user.getUpdatedAt());

        return user;
    }

    public List<Map<String, Object>> findAll() {
        String sql = "SELECT * FROM users;";
        System.out.println(jdbcTemplate.queryForList(sql));
        return jdbcTemplate.queryForList(sql);
    }

}
