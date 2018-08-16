package com.ilongli.web.filter;

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
public class Filter1_ProjectFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(Filter1_ProjectFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//TODO
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		LOGGER.info("请求路径："+uri);
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		//TODO
	}

}
