package com.ilongli.shiro;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ilongli.config.JdbcConfig;
import com.ilongli.config.RootConfig;
import com.ilongli.config.ShiroConfig;
import com.ilongli.config.WebConfig;
import com.ilongli.entity.Permission;
import com.ilongli.entity.Role;
import com.ilongli.entity.User;
import com.ilongli.service.PermissionService;
import com.ilongli.service.RoleService;
import com.ilongli.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitWebConfig(classes = {JdbcConfig.class, RootConfig.class, WebConfig.class, ShiroConfig.class})
@Rollback(value=true)
public class ShiroTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;


    protected String password = "123456";

    protected Permission p1;
    protected Permission p2;
    protected Permission p3;
    protected Role r1;
    protected Role r2;
    protected User u1;
    protected User u2;
    protected User u3;
    protected User u4;
    
    @Before
    public void setUp() {
        //1、新增权限
        p1 = new Permission("user:create", "用户模块新增", Boolean.TRUE);
        p2 = new Permission("user:update", "用户模块修改", Boolean.TRUE);
        p3 = new Permission("menu:create", "菜单模块新增", Boolean.TRUE);
        permissionService.save(p1);
        permissionService.save(p2);
        permissionService.save(p3);
        //2、新增角色
        r1 = new Role("admin", "管理员", Boolean.TRUE);
        r2 = new Role("user", "用户管理员", Boolean.TRUE);
        roleService.save(r1);
        roleService.save(r2);
        //3、关联角色-权限
        roleService.correlationPermissions(r1.getId(), p1.getId());
        roleService.correlationPermissions(r1.getId(), p2.getId());
        roleService.correlationPermissions(r1.getId(), p3.getId());

        roleService.correlationPermissions(r2.getId(), p1.getId());
        roleService.correlationPermissions(r2.getId(), p2.getId());

        //4、新增用户
        u1 = new User("zhang", password);
        u2 = new User("li", password);
        u3 = new User("wu", password);
        u4 = new User("wang", password);
        u4.setLocked(Boolean.TRUE);
        userService.createUser(u1);
        userService.createUser(u2);
        userService.createUser(u3);
        userService.createUser(u4);
        //5、关联用户-角色
        userService.correlationRoles(u1.getId(), r1.getId());

    }

    @Test
    public void test() {
/*        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(u1.getUsername(), password);
        subject.login(token);

        Assert.assertTrue(subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkPermission("user:create");

        userService.changePassword(u1.getId(), password + "1");
        userRealm.clearCache(subject.getPrincipals());

        token = new UsernamePasswordToken(u1.getUsername(), password + "1");
        subject.login(token);*/
    }

}