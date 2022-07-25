package com.onlinechat.service.impl;

import com.onlinechat.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName : RedisServiceImpl
 * @Description :
 * @Author : y
 * @Date: 2021-06-29 20:22
 */

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void setRedisKeyValueWithTimeout(String key, String value, int time, TimeUnit timeUnit) {

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        operations.set(key, value, time, timeUnit);

    }

    @Override
    public String getRedisValueByKey(String key) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();


        String value = operations.get(key);

        return value;
    }

    @Override
    public void removeRedisKeyRemote(String key) {
        redisTemplate.delete(key);

    }
}
