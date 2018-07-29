package com.ilongli.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * springmvc配置类
 * @author ilongli
 *
 */
@Configuration
@ComponentScan("com.ilongli.controller")
public class WebConfig extends WebMvcConfigurationSupport {

    /** 
     * 配置静态资源的处理 
     * 将请求交由Servlet处理,不经过DispatchServlet 
     */  
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){  
        configurer.enable();  
    }  
	
	/**
	 * 配置静态资源的根路径
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
	
	/**
	 * 配置视图解析器
	 * @return
	 */
	@Bean
	public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();//jsp视图解析器  
        resolver.setPrefix("/WEB-INF/view/");  
        resolver.setSuffix(".jsp");  
        resolver.setExposeContextBeansAsAttributes(true);  
        return resolver; 
	}
	
	/**
	 * 配置FastJSON
	 */
	@Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter());
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        ArrayList<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.valueOf("text/html;charset=UTF-8"));
        list.add(MediaType.valueOf("application/json;charset=UTF-8"));
        fastJsonHttpMessageConverter.setSupportedMediaTypes(list);
        converters.add(fastJsonHttpMessageConverter);
    }
	
	
}
