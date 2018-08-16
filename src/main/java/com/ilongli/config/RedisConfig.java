package com.ilongli.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.ilongli.redis.RedisPool;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置类
 * @author ilongli
 *
 */
@Configuration
public class RedisConfig {

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		return jedisPoolConfig;
	}
	
	@Bean
	public RedisPool redisPool() {
		RedisPool redisPool = new RedisPool();
		redisPool.setServerIp("23.105.199.24");
		
		return null;
	}
	
	@Bean
	public RedisTemplate redisTemplate() {
		RedisTemplate redisTemplate = new RedisTemplate();
		return null;
	}
}
