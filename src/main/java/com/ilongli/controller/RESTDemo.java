package com.ilongli.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public String testfm(Model model) {
		model.addAttribute("user", "ilongli");
		return "testfm";
	}
	
	@RequestMapping("teste")
	public String testfm() {
		//Throw Exception
		int i = 1/0;
		return "test";
	}
	
	/**
	 * test
	 */
    @RequestMapping("/hello1")
    public String hello1() {
        SecurityUtils.getSubject().checkRole("admin");
        return "success";
    }

    @RequiresRoles("admin")
    @RequestMapping("/hello2")
    public String hello2() {
        return "success";
    }
    
    
}
