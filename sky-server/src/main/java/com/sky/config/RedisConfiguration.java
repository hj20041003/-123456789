package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("开始创建redis对象...");
        RedisTemplate redisTemplate = new RedisTemplate();
//        设置redis连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //        设置值的序列化器（使用Jackson，支持Integer等任意对象类型）
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //        设置键的序列化器（使用String，保证key可读）
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}