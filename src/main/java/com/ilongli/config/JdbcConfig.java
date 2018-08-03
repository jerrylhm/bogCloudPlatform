package com.ilongli.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 所有持久层的相关配置
 * @author ilongli
 *
 */
@Configuration
@PropertySource(value = "classpath:properties/jdbc.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages="com.ilongli.repository", entityManagerFactoryRef="entityManagerFactory")
public class JdbcConfig {
	
	private static final Logger LOGGER = LogManager.getLogger(JdbcConfig.class);
	
	@Value(value = "${jdbc.driver}")
	private String driver;
	
	@Value(value = "${jdbc.url}")
	private String url;
	
	@Value(value = "${jdbc.username}")
	private String username;
	
	@Value(value = "${jdbc.password}")
	private String password;
	
	@Value(value = "${jdbc.initialSize}")
	private int initialSize;
	
	@Value(value = "${jdbc.minIdle}")
	private int minIdle;
	
	@Value(value = "${jdbc.maxActive}")
	private int maxActive;
	
	@Value(value = "${jdbc.maxWait}")
	private long maxWait;
	
	@Value(value = "${jdbc.timeBetweenEvictionRunsMillis}")
	private long timeBetweenEvictionRunsMillis;
	
	@Value(value = "${jdbc.minEvictableIdleTimeMillis}")
	private long minEvictableIdleTimeMillis;
	
	@Value(value = "${jdbc.validationQuery}")
	private String validationQuery;
	
	@Value(value = "${jdbc.testWhileIdle}")
	private boolean testWhileIdle;
	
	@Value(value = "${jdbc.testOnBorrow}")
	private boolean testOnBorrow;
	
	@Value(value = "${jdbc.testOnReturn}")
	private boolean testOnReturn;
	
	@Value(value = "${jdbc.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	
	@Value(value = "${jdbc.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;
	
	@Value(value = "${jdbc.filters}")
	private String filters;
	
	@Value(value = "${jdbc.connectionProperties}")
	private String connectionProperties;
	

	/**
	 * 配置数据源
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		
		druidDataSource.setDriverClassName(driver);
		druidDataSource.setUrl(url);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);
		druidDataSource.setInitialSize(initialSize);
		druidDataSource.setMinIdle(minIdle);
		druidDataSource.setMaxActive(maxActive);
		druidDataSource.setMaxWait(maxWait);
		druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		druidDataSource.setValidationQuery(validationQuery);
		druidDataSource.setTestWhileIdle(testWhileIdle);
		druidDataSource.setTestOnBorrow(testOnBorrow);
		druidDataSource.setTestOnReturn(testOnReturn);
		druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			druidDataSource.setFilters(filters);
		} catch (SQLException e) {
			LOGGER.error("druid监控统计拦截filters启动失败。", e);
		}
		druidDataSource.setConnectionProperties(connectionProperties);
		
		return druidDataSource;
	}
	
	/**
	 * 配置jpaVendorAdapter
	 * @return
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		return hibernateJpaVendorAdapter;
	}
	
	/**
	 * 配置EntityManagerFactory
	 * @param dataSource
	 * @param jpaVendorAdapter
	 * @return
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
            JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);
		lef.setPackagesToScan("com.ilongli");
		
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
		jpaProperties.setProperty("hibernate.dialecthibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		jpaProperties.setProperty("hibernate.show_sql", "true");
		jpaProperties.setProperty("hibernate.format_sql", "true");
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		jpaProperties.setProperty("hibernate.id.new_generator_mappings", "false");
		lef.setJpaProperties(jpaProperties);
		
		return lef;
	}
	
	/**
	 * 配置事务管理器
	 * @param entityManagerFactory
	 * @return
	 */
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
		return jpaTransactionManager;
	}
	
	/**
	 * 配置JdbcTemplate
	 * @return
	 */
	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
	
}
