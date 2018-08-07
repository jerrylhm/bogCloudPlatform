package com.ilongli.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
	/**
	 * 这里使用env获取配置文件里面的值
	 * 注意：当配置了shiro后，这里无法使用@Value注解获取，如
	 * 		@Value(value = "${jdbc.driver}")
	 *		private String driver; 
	 * 具体原因未知，经初步判断，当ShiroConfig内的Bean注入DefaultWebSecurityManager securityManager属性的时候，@Value则会失效
	 * 比如把ShiroConfig内的methodInvokingFactoryBean()和shiroFilter()注释之后，@Value注解则可正常工作。
	 */
    @Autowired
    Environment env;
	
	private static final Logger LOGGER = LogManager.getLogger(JdbcConfig.class);

	/**
	 * 配置数据源
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		
		/**
		 * 这里以后考虑使用反射自动注入配置文件属性值
		 */
		//TODO
		druidDataSource.setDriverClassName(env.getProperty("jdbc.driver"));
		druidDataSource.setUrl(env.getProperty("jdbc.url"));
		druidDataSource.setUsername(env.getProperty("jdbc.username"));
		druidDataSource.setPassword(env.getProperty("jdbc.password"));
		druidDataSource.setInitialSize(Integer.parseInt(env.getProperty("jdbc.initialSize")));
		druidDataSource.setMinIdle(Integer.parseInt(env.getProperty("jdbc.minIdle")));
		druidDataSource.setMaxActive(Integer.parseInt(env.getProperty("jdbc.maxActive")));
		druidDataSource.setMaxWait(Long.parseLong(env.getProperty("jdbc.maxWait")));
		druidDataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(env.getProperty("jdbc.timeBetweenEvictionRunsMillis")));
		druidDataSource.setMinEvictableIdleTimeMillis(Long.parseLong(env.getProperty("jdbc.minEvictableIdleTimeMillis")));
		druidDataSource.setValidationQuery(env.getProperty("jdbc.validationQuery"));
		druidDataSource.setTestWhileIdle(Boolean.parseBoolean(env.getProperty("jdbc.testWhileIdle")));
		druidDataSource.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("jdbc.testOnBorrow")));
		druidDataSource.setTestOnReturn(Boolean.parseBoolean(env.getProperty("jdbc.testOnReturn")));
		druidDataSource.setPoolPreparedStatements(Boolean.parseBoolean(env.getProperty("jdbc.poolPreparedStatements")));
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(env.getProperty("jdbc.maxPoolPreparedStatementPerConnectionSize")));
		try {
			druidDataSource.setFilters(env.getProperty("jdbc.filters"));
		} catch (SQLException e) {
			LOGGER.error("druid监控统计拦截filters启动失败。", e);
		}
		druidDataSource.setConnectionProperties(env.getProperty("jdbc.connectionProperties"));
		
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
		//TODO 抽取到配置文件
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
