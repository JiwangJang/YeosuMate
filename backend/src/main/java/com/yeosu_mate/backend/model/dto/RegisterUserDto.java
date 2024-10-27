package com.yeosu_mate.backend.model.dto;

import com.yeosu_mate.backend.model.emums.RoleEnum;
import com.yeosu_mate.backend.util.UtilMethod;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String userId;
    private String nickname = UtilMethod.nicknameGenerator();
    private String password;
    private String phoneNumber;
    private RoleEnum role = RoleEnum.USER;
    private String profileImage = "https://de9nqjthi7764.cloudfront.net/%EC%9D%91%EC%9A%A9%EC%84%AC3.png";
}
