package com.practice.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.practice.model.User;

@Repository
public class UserRepository {
    private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean insertUser(User user, String id, String pw) {
        String sql = "INSERT INTO userdatas(id, pw, username, age) VALUE(?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, id, pw, user.getUsername(), user.getAge());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserPassword(String id) throws DataAccessException {
        String sql = "SELECT pw FROM userdatas WHERE id=?";
        RowMapper<String> pwRowMapper = (r, i) -> r.getString("pw");

        return jdbcTemplate.queryForObject(sql, pwRowMapper, id);

    }

    public User getUser(String id) throws DataAccessException {
        String sql = "SELECT username, age FROM userdatas WHERE id=?";
        RowMapper<User> pwRowMapper = (r, i) -> {
            User emptyUser = new User();
            emptyUser.setUsername(r.getString("username"));
            emptyUser.setAge(r.getInt("age"));
            return emptyUser;
        };
        return jdbcTemplate.queryForObject(sql, pwRowMapper, id);
    }
}
