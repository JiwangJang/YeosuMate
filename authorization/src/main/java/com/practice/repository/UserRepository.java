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

    public boolean insertUser(User user) throws DataAccessException {
        String sql = "INSERT INTO userdatas(id, pw, username, age) VALUE(?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getPassword(), user.getUsername(), user.getAge());
        return true;
    }

    public User getUser(String id) throws DataAccessException {
        String sql = "SELECT username, age FROM userdatas WHERE id=?";
        RowMapper<User> pwRowMapper = (row, i) -> {
            User emptyUser = new User();
            emptyUser.setId(row.getString("id"));
            emptyUser.setPassword(row.getString("pw"));
            emptyUser.setUsername(row.getString("username"));
            emptyUser.setAge(row.getInt("age"));
            return emptyUser;
        };
        return jdbcTemplate.queryForObject(sql, pwRowMapper, id);
    }
}
