package com.ilongli.config;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ilongli.entity.Role;
import com.ilongli.entity.UserRole;
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
		UserRole userRole = new UserRole();
		userRole.setRoleId(1L);
		userRole.setUserId(1L);
		RedisUtil.valueSet("userRole", userRole);
	}
	
	@Test 
	public void testGetObject() {
//		UserRole userRole = (UserRole) RedisUtil.valueGet("userRole");
//		System.out.println(userRole);
		SimpleSession session = (SimpleSession) RedisUtil.valueGet("shiro_redis_session:337c2680-3032-4e53-ad1d-598323478505");
		System.out.println(session.getId());
	}
}
