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

    RowMapper<User> UserdataRowMapper = (row, i) -> {
        User emptyUser = new User();
        emptyUser.setId(row.getString("id"));
        emptyUser.setPassword(row.getString("password"));
        emptyUser.setUsername(row.getString("username"));
        emptyUser.setAge(row.getInt("age"));
        emptyUser.setRefreshToken(row.getString("refresh_token"));
        return emptyUser;
    };

    public boolean insertUser(User user) throws DataAccessException {
        String sql = "INSERT INTO userdatas(id, password, username, age) VALUE(?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getPassword(), user.getUsername(), user.getAge());
        return true;
    }

    public void refreshTokenUpdate(String refreshToken, String username) throws DataAccessException {
        String sql = "UPDATE userdatas SET refresh_token=? WHERE username=?";
        jdbcTemplate.update(sql, refreshToken, username);
    }

    public User getUserById(String id) throws DataAccessException {
        String sql = "SELECT username, age FROM userdatas WHERE id=?";
        return jdbcTemplate.queryForObject(sql, UserdataRowMapper, id);
    }

    public User getUserByRefreshToken(String refreshToken) throws DataAccessException {
        String sql = "SELECT username, age FROM userdatas WHERE refresh_token=?";
        return jdbcTemplate.queryForObject(sql, UserdataRowMapper, refreshToken);
    }

    public User getUserByUsername(String username) throws DataAccessException {
        String sql = "SELECT username, age FROM userdatas WHERE username=?";
        return jdbcTemplate.queryForObject(sql, UserdataRowMapper, username);
    }

}
