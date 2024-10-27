package com.yeosu_mate.backend.util;

import java.util.*;

public class UtilMethod {
    private UtilMethod() {
    }

    private static final List<Character> characters = List.of(
            'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'z', 'x',
            'c', 'v', 'b', 'n', 'm', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0');

    private static final Random random = new Random();

    private static Character getRandomCharacter() {
        int randomInt = random.nextInt(36);
        return characters.get(randomInt);
    }

    /**
     * 기본 랜덤 닉네임 생성기
     * 
     * @return String
     */
    public static String nicknameGenerator() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(getRandomCharacter());
        }
        return stringBuilder.toString();
    }
}
