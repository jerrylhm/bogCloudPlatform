package com.ilongli.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLog4j2 {
	
	public static final Logger LOGGER = LogManager.getLogger(TestLog4j2.class);

	public static void main(String[] args) {
        LOGGER.trace("trace level");
        LOGGER.debug("debug level");
        LOGGER.info("info level");
        LOGGER.warn("warn level");
        LOGGER.error("error level");
        LOGGER.fatal("fatal level");
	}
}
