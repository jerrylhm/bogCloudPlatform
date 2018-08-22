package com.ilongli.config;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ilongli.entity.User;
import com.ilongli.utils.RedisUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitWebConfig(classes = {RedisConfig.class, JdbcConfig.class, ShiroConfig.class, RootConfig.class, WebConfig.class})
public class TestRedis {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void test() {
		System.out.println("name : " + RedisUtil.valueGet("name"));
	}
	
	@Test
	public void testTimeOut() {
		RedisUtil.valueSet("username", "ilongli", 10, TimeUnit.SECONDS);
	}
	
	@Test
	public void del() {
		RedisUtil.delete("object_user");
	}
	
	@Test
	public void testSaveObject() {
/*		UserRole userRole = new UserRole();
		userRole.setRoleId(1L);
		userRole.setUserId(1L);
		RedisUtil.valueSet("userRole", userRole);*/
		
		User user = new User("jack", "123456");
		RedisUtil.valueSet("user", user);
	}
	
	@Test 
	public void testGetObject() {
/*		UserRole userRole = (UserRole) RedisUtil.valueGet("userRole");
		System.out.println(userRole);*/
//		User user = (User) RedisUtil.valueGet("user");
//		System.out.println(user);
		Session session = (Session) RedisUtil.valueGet("shiro_redis_session:6e8ea40a-2d07-4111-9798-ab86031948f1");
		System.out.println(session.getId());
	}
}
