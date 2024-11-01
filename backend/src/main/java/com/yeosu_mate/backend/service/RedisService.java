package com.yeosu_mate.backend.service;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;
    private final HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();

    /**
     * Redis에 문자열 키&값을 저장하는 메서드
     * 
     * @param key
     * @param value
     * @param expiredTime 만료시간(초 단위)
     */
    public void setString(String key, String value) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    /**
     * Redis에 저장돼 있는 거 꺼내오는 메서드
     * 
     * @param key
     * @return value
     */
    public String getString(String key) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setObject(String key, Object object) {

    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }
}
