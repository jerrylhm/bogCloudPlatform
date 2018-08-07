package com.ilongli.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * spring配置类
 * @author ilongli
 *
 */
@Configuration  
@ComponentScan(basePackages={"com.ilongli.service", "com.ilongli.web.exception"}) 
public class RootConfig {
	
	
}
