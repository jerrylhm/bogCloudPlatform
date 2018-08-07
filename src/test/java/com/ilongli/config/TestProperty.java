package com.ilongli.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "classpath:properties/jdbc.properties")
public class TestProperty {

    @Autowired
    Environment env;
    
    @Bean
    public String alarm() {
        System.out.println(env.getProperty("jdbc.type"));
        return "1";
    }
}
