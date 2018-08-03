package com.ilongli.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ilongli.config.JdbcConfig;
import com.ilongli.config.RootConfig;
import com.ilongli.config.WebConfig;
import com.ilongli.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitWebConfig(classes = {JdbcConfig.class, RootConfig.class, WebConfig.class})
public class TestUserService {

	@Autowired
	private UserService userService;
	
	@Test
	public void test() {
		User user = new User();
		user.setName("rose");
		user.setAge(12);
		User add = userService.save(user);
		System.out.println(add);
	}
}
