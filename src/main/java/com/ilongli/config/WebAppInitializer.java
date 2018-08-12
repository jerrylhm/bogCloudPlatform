package com.ilongli.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * web初始化类,替代web.xml 
 * 继承AbstractAnnotationConfigDispatcherServletInitializer类后,
 * 会同时创建DispatcherServlet和ContextLoaderListener 
 * 前提:Servlet3.0
 * @author ilongli
 *
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /** 
     * RootConfig配置类加载的是驱动应用后端的中间层和数据层组件，是父上下文
     */  
    @Override  
    protected Class<?>[] getRootConfigClasses() {  
        return new Class<?>[]{RootConfig.class, JdbcConfig.class, ShiroConfig.class};  
    }  
  
    /** 
     * WebConfig配置类中主要是内容是启用组件扫描，配置视图解析器，配置静态资源的处理
     */  
    @Override  
    protected Class<?>[] getServletConfigClasses() {  
        return new Class<?>[]{WebConfig.class};  
    }  
  
    /** 
     * 配置ServletMappings (<servlet-mapping>)
     */  
    @Override  
    protected String[] getServletMappings() {  
    	//将DispatcherServlet映射到"/"
        return new String [] {"/"};  
    } 
    
}
