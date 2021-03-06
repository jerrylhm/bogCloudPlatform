package com.ilongli.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ilongli.utils.PropertiesUtil;
import com.ilongli.web.interceptor.MyInterceptor;
import com.ilongli.web.resolver.CurrentUserMethodArgumentResolver;

/**
 * springmvc配置类
 * @author ilongli
 *
 */
@Configuration
@ComponentScan("com.ilongli.controller")
public class WebConfig extends WebMvcConfigurationSupport {
	
	private static final Logger LOGGER = LogManager.getLogger(WebConfig.class);

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
    	registry.addResourceHandler("/**").addResourceLocations("classpath:/");
    }
	
	/**
	 * 配置FreeMarker
	 */
	@Bean
	public FreeMarkerConfig freeMarkerConfig() {
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/view/");
		freeMarkerConfigurer.setDefaultEncoding("UTF-8");
		
		Properties fmProperties = new Properties();
		try {
			PropertiesUtil.loadProperties(fmProperties, getClass().getClassLoader().getResource("properties/freemarker.properties").getPath());
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		freeMarkerConfigurer.setFreemarkerSettings(fmProperties);
/*		freemarker.template.Configuration configuration = 
				new freemarker.template.Configuration(freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);*/
		//configuration.setDefaultEncoding("UTF-8");
		//configuration.setOutputEncoding("UTF-8");
		//configuration.setLocale(Locale.SIMPLIFIED_CHINESE);
/*		configuration.setDateFormat("yyyy-MM-dd");
		configuration.setTimeFormat("HH:mm:ss");
		configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
		configuration.setClassicCompatible(true);//空串显示""*/
/*		configuration.setTemplateLoader(new WebappTemplateLoader(getServletContext()));
		freeMarkerConfigurer.setConfiguration(configuration);*/
		
		return freeMarkerConfigurer;
	}
	
	/**
	 * 配置FreeMarker视图解析器
	 */
	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver() {
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		//viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".ftl");
		viewResolver.setCache(true);
		viewResolver.setContentType("text/html;charset=UTF-8");
		viewResolver.setRequestContextAttribute("rc");
		viewResolver.setOrder(0);
        return viewResolver; 
	}
	
	/**
	 * 配置jsp视图解析器
	 */
/*	@Bean
	public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/view/");  
        resolver.setSuffix(".jsp");  
		resolver.setExposeContextBeansAsAttributes(true);  
		return resolver;
	}*/
	
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
	
	/**
	 * 添加拦截器
	 */
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/hello1");	
	}
	
	/**
	 * 添加参数解析器
	 */
	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new CurrentUserMethodArgumentResolver());
	}
	
	/**
	 * 开启 Shiro Spring AOP，这样才能在相应控制器使用shiro注解
	 * 注意：这个两个bean必须配置在WebConfig(或spring-mvc.xml文件)下，即必须作为springmvc容器加载
	 * dependsOn为非必要配置项，待考证
	 */
    @Bean
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
    	AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
    	authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
    	return authorizationAttributeSourceAdvisor;
    }
}
