package com.practice.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.practice.entities.Role;
import com.practice.entities.RoleEnum;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Repository
@Slf4j
public class RoleRepository {
    private JdbcTemplate jdbcTemplate;

    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Role> findByName(RoleEnum name) throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM roles WHERE name=?";

        try {
            List<Role> role = jdbcTemplate.query(sql, (r, i) -> {
                Role newRole = new Role();
                newRole.setId(r.getInt("id"));
                newRole.setName(RoleEnum.valueOf(r.getString("name")));
                newRole.setDescription(r.getString(("description")));
                newRole.setCreatedAt(r.getTimestamp("createdAt"));
                newRole.setUpdatedAt(r.getTimestamp("updatedAt"));

                return newRole;
            }, name.name());
            return Optional.ofNullable(role.get(0));
        } catch (Exception e) {
            log.error("error", e);
            return Optional.empty();
        }
    }

    public void save(Role role) {
        String sql = "INSERT INTO roles(name, description, createdAt, updatedAt) VALUE(?, ?, ?, ?);";

        jdbcTemplate.update(sql, role.getName().toString(), role.getDescription(), role.getCreatedAt(),
                role.getUpdatedAt());
    }
}
