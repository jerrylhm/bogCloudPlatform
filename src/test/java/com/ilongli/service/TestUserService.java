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
import com.ilongli.repository.PermissionRepository;
import com.ilongli.repository.RoleRepository;
import com.ilongli.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitWebConfig(classes = {JdbcConfig.class, RootConfig.class, WebConfig.class})
public class TestUserService {

	@Autowired
	private UserRepository userRepository;
/*	
	@Autowired
	private UserService userService;*/
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Test
	public void test() {
		
/*		User user = userRepository.findByUsername("zhang");
		System.out.println(user);
		
		Set<String> roles = userRepository.findRoles("zhang");
		System.out.println(roles);
		
		Set<String> permissions = userRepository.findPermissions("zhang");
		System.out.println(permissions);*/
		
//		System.out.println(userRepository.correlationRole(1L, 2L));
		
//		System.out.println(userRepository.uncorrelationRole(1L, 2L));
		
//		System.out.println(userRepository.exists(1L, 2L));
		
//		System.out.println(roleRepository.correlationPermission(2L, 2L));
		
//		System.out.println(roleRepository.uncorrelationPermission(2L, 2L));
		
//		System.out.println(roleRepository.exists(1L, 1L));
		
//		System.out.println(roleRepository.deleteUserRole(2L));
		
		System.out.println(permissionRepository.deleteRolePermission(2L));
	}
}
