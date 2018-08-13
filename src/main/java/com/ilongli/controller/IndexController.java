package com.ilongli.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ilongli.entity.User;
import com.ilongli.web.annotation.CurrentUser;

@Controller
public class IndexController {
	
	/**
	 * 登录入口
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest req, Model model,
			@RequestParam(value = "kickout", required = false) String kickout) {
		String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
		String error = null;
		if(ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
			error = "你已重复尝试超过5次，请1个小时之后重试";
		}else if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
			error = "用户不存在";
		} else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(exceptionClassName != null) {
            error = exceptionClassName;
        } else if (kickout != null) {
        	error = "您被踢出登录";
        }
		model.addAttribute("error", error);
		return "login";
	}
	
	/**
	 * 主页入口
	 */
	@RequestMapping("")
	public String index(@CurrentUser User loginUser, Model model) {
		model.addAttribute("username", loginUser.getUsername());
		return "index";
	}
	
	/**
	 * 404错误返回的页面 
	 */
	@RequestMapping("*")
	public String noHandlerFound() {
		return "error/error_404";
	}
	
	/**
	 * 没有权限返回页面
	 */
	@RequestMapping("unauthorized")
	public String unauthorized() {
		return "unauthorized";
	}
}
