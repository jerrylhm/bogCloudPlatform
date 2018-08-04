package com.ilongli.web.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebFilter(filterName="projectFilter", urlPatterns={ "/*" })
/*@Order(value = 1)*/
public class ProjectFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(ProjectFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//TODO
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
/*		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		LOGGER.info("请求路径："+uri);*/
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		//TODO
	}

}
