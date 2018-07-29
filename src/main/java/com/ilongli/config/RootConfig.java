package com.ilongli.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * spring配置类
 * @author ilongli
 *
 */
@Configuration  
@ComponentScan(basePackages={"com.ilongli"},
			   excludeFilters={@Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)}) 
public class RootConfig {
	
}
