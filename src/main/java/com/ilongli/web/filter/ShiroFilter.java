package com.ilongli.web.filter;

import javax.servlet.DispatcherType;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.springframework.web.filter.DelegatingFilterProxy;

@WebFilter(filterName="shiroFilter", urlPatterns="/*", initParams={
	@WebInitParam(name="targetFilterLifecycle", value="true")
},asyncSupported=true,dispatcherTypes=DispatcherType.REQUEST)
public class ShiroFilter extends DelegatingFilterProxy {
	
}
