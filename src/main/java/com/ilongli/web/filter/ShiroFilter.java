package com.ilongli.web.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.DelegatingFilterProxy;

@WebFilter(filterName="shiroFilter", urlPatterns="/*", initParams={
	@WebInitParam(name="targetFilterLifecycle", value="true")
},asyncSupported=true)
@Order(1)
public class ShiroFilter extends DelegatingFilterProxy {

}
