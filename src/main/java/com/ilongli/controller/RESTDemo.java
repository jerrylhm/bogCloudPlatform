package com.ilongli.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilongli.service.UserService;

@RequestMapping("test")
@Controller
public class RESTDemo {
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	public String queryById(@PathVariable("id") Long id) {
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " : " + id);
		return "get id : " + id;
	}
	
	
	@RequestMapping("testfm")
	public String testfm(Map<String,Object> map) {
		map.put("user", "ilongli");
		return "testfm";
	}
}
