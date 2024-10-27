package com.yeosu_mate.backend.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.yeosu_mate.backend.model.dto.RegisterUserDto;
import com.yeosu_mate.backend.model.emums.RoleEnum;
import com.yeosu_mate.backend.model.entity.User;
import com.yeosu_mate.backend.model.process.ProcessResult;

import lombok.extern.slf4j.Slf4j;

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

    public int getRoleId(RoleEnum role) {
        String sql = "SELECT roleId FROM roles WHERE role=?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, role.name());
        result.next();
        return result.getInt("roleId");
    }

    public ProcessResult registerUser(RegisterUserDto registerUserDto) {
        String sql = "INSERT INTO users(userId, nickname, phoneNumber, password, profileImage, roleId) VALUE(?, ?, ? ,? ,?, ?);";
        int roleId = getRoleId(registerUserDto.getRole());

        jdbcTemplate.update(sql, registerUserDto.getUserId(), registerUserDto.getNickname(),
                registerUserDto.getPhoneNumber(), registerUserDto.getPassword(), registerUserDto.getProfileImage(),
                roleId);
        return new ProcessResult(true, "");
    }
}
