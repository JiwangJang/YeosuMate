package com.yeosu_mate.backend.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class RedisService {
    RedisTemplate<String, Object> redisTemplate;
    StringRedisTemplate stringRedisTemplate;

    /**
     * Redis에 문자열 키&값을 저장하는 메서드
     * 
     * @param key
     * @param value
     * @param expiredTime 만료시간(초 단위)
     */
    public void setString(String key, String value, int expiredTime) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value, expiredTime, TimeUnit.SECONDS);
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

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
