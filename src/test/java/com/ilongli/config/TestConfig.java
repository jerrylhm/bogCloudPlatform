package com.ilongli.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitWebConfig(classes = {JdbcConfig.class, RootConfig.class, WebConfig.class})
public class TestConfig {

	
	@Test
	public void test() {
	}
}
