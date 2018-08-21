package com.ilongli.config;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitWebConfig(classes = {RedisConfig.class, JdbcConfig.class, ShiroConfig.class, RootConfig.class, WebConfig.class})
public class TestRedis {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void test() {
		redisTemplate.opsForValue().set("name", "jack");
		System.out.println("name : " + redisTemplate.opsForValue().get("name"));
	}
	
	@Test
	public void testTimeOut() {
		redisTemplate.opsForValue().set("username", "ilongli", 10, TimeUnit.SECONDS);
	}
}
