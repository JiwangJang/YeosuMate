package com.yeosu_mate.backend.model.dto;

import lombok.Data;

/**
 * 로그인과정에서 사용자가 인증을 얼만큼 했는지 체크하는 객체
 */
@Data
public class RegisterProcessDto {
    /**
     * 핸드폰 인증완료했는지 체크
     */
    boolean phoneAuthentcation;

    /**
     * 비밀번호 중복검사했는지 체크
     */
    boolean passwordAuthenticaiton;

    /**
     * 동일아이디 있는지 체크
     */
    boolean sameIdAuthentication;
}
