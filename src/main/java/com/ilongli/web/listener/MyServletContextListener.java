package com.ilongli.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

@WebListener
public class MyServletContextListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//开启项目的时候设置 Log4j2配置文件的路径
		Configurator.initialize("Log4j2", getClass().getClassLoader().getResource("properties/log4j2.xml").getPath());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//关闭项目时关闭LogManager，防止Log4j2的内存泄漏
		LogManager.shutdown();
	}
}
