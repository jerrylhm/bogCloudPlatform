package com.ilongli.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

public class TestLog4j2 {

	public static void main(String[] args) throws FileNotFoundException {
			
		
		String path = Thread.currentThread().getContextClassLoader().getResource("properties/log4j2.xml").getPath();
		Configurator.initialize("Log4j2", path);
		
		System.out.println(path);
		
		Logger LOGGER = LogManager.getLogger(TestLog4j2.class);
		
        LOGGER.trace("trace level");
        LOGGER.debug("debug level");
        LOGGER.info("info level");
        LOGGER.warn("warn level");
        LOGGER.error("error level");
        LOGGER.fatal("fatal level");
	}
}
