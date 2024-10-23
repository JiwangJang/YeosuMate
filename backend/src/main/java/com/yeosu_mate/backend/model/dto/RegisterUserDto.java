package com.yeosu_mate.backend.model.dto;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String id;
    private String password;
    private String nickname;
}
