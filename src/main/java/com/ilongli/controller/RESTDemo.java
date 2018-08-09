package com.ilongli.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilongli.service.UserService;

@RequestMapping("test")
@Controller
public class RESTDemo {
	
	private static final Logger LOGGER = LogManager.getLogger(RESTDemo.class);
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	public String queryById(@PathVariable("id") Long id) {
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " : " + id);
		System.out.println(userService.findAll());
		return "get id : " + id;
	}
	
	
	@RequestMapping("testfm")
	public String testfm(Map<String,Object> map) {
		map.put("user", "ilongli");
		return "testfm";
	}
	
	@RequestMapping("index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("login")
	public String login(HttpServletRequest req, Map<String,Object> map,
			@RequestParam(value = "kickout", required = false) String kickout) {
		String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
		String error = null;
		if(ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
			error = "你已重复尝试超过5次，请1个小时之后重试。";
		}else if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		} else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        } else if (kickout != null) {
        	error = "您被踢出登录！";
        }
		map.put("error", error);
		return "login";
	}
	
	@RequestMapping("unauthorized")
	public String unauthorized() {
		return "unauthorized";
	}
}
