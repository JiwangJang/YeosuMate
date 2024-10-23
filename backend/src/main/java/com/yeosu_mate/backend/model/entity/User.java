package com.yeosu_mate.backend.model.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

import java.util.*;

@Data
public class User implements UserDetails {
    private String id;
    private String password;
    private String nickname;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.nickname;
    }
}