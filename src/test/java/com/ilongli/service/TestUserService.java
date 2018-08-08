package com.ilongli.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ilongli.config.JdbcConfig;
import com.ilongli.config.RootConfig;
import com.ilongli.config.ShiroConfig;
import com.ilongli.config.TestProperty;
import com.ilongli.config.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitWebConfig(classes = {JdbcConfig.class, RootConfig.class, WebConfig.class, ShiroConfig.class, TestProperty.class})
public class TestUserService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;
	

	
	@Test
	public void test() {
		
		/**
		 * testService
		 */
		//System.out.println(userService.correlationRoles(2L, 1L, 2L));
		
//		System.out.println(userService.uncorrelationRoles(2L, 1L, 2L));
//		
//		System.out.println(userService.findByUsername("zhang"));
//		
//		System.out.println(userService.findRoles("zhang"));
//		
//		System.out.println(userService.findPermissions("zhang"));
		
		
//		System.out.println(roleService.correlationPermissions(1L, 1L, 2L));
	
//		System.out.println(roleService.uncorrelationPermissions(1L, 1L, 2L));
		
//		System.out.println(roleService.exists(1L, 1L));
//		
//		System.out.println(roleService.deleteUserRole(1L));
		
		
//		Permission permission = new Permission();
//		permission.setPermission("user:create");
//		permission.setDescription("创建用户");
//		System.out.println(permissionService.save(permission));
		
//		System.out.println(permissionService.deletePermission(1L));
		
		
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
		
//		System.out.println(permissionRepository.deleteRolePermission(2L));
	}
}
