package com.onlinechat.service;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName : RedisSetvice
 * @Description :
 * @Author : y
 * @Date: 2021-06-29 20:22
 */
public interface RedisService {
    // 向redis里存一个带有时间的key
    void setRedisKeyValueWithTimeout(String key, String value, int time, TimeUnit timeUnit);

    // 根据手机号在redis查询发送的验证码
    String getRedisValueByKey(String key);

    void removeRedisKeyRemote(String key);
}
