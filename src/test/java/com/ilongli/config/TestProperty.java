package com.ilongli.config;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.ilongli.utils.PropertiesUtil;

@Configuration
@PropertySource(value = "classpath:properties/jdbc.properties")
public class TestProperty {

    @Autowired
    Environment env;
    
    @Bean
    public String alarm() throws IOException {
    	
    	Properties prop = new Properties();
    	PropertiesUtil.loadProperties(prop, getClass().getClassLoader().getResource("properties/hibernate.properties").getPath());

    	Iterator<String> it=prop.stringPropertyNames().iterator();
        while(it.hasNext()){
            String key=it.next();
            System.out.println(key+":"+prop.getProperty(key));
        }
        

        return "1";
    }
}
