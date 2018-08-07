package com.ilongli.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ilongli.service.UserService;
import com.ilongli.shiro.credentials.RetryLimitHashedCredentialsMatcher;
import com.ilongli.shiro.realm.UserRealm;

/**
 * shiro配置类
 * @author ilongli
 *
 */
@Configuration
public class ShiroConfig {

	/**
	 * 配置缓存管理器，使用EhCache实现
	 */
	@Bean
	public EhCacheManager cacheManager() {
		EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManagerConfigFile("classpath:properties/ehcache.xml");
		return ehCacheManager;
	}
	
	/**
	 * 配置凭证匹配器
	 */
	@Bean
	public RetryLimitHashedCredentialsMatcher credentialsMatcher(EhCacheManager cacheManager) {
		RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager);
		credentialsMatcher.setHashAlgorithmName("md5");
		credentialsMatcher.setHashIterations(2);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		return credentialsMatcher;
	}
	
	/**
	 * 配置userRealm实现
	 */
	@Bean
	public UserRealm userRealm(UserService userService, RetryLimitHashedCredentialsMatcher credentialsMatcher) {
		UserRealm userRealm = new UserRealm();
		//TODO
		//userService?
		userRealm.setCredentialsMatcher(credentialsMatcher);
		userRealm.setCachingEnabled(true);
		userRealm.setAuthenticationCacheName("authenticationCache");
		userRealm.setAuthorizationCachingEnabled(true);
		userRealm.setAuthorizationCacheName("authorizationCache");
		return userRealm;
	}
	
	/**
	 * 配置会话ID生成器
	 */
	@Bean
	public JavaUuidSessionIdGenerator sessionIdGenerator() {
		return new JavaUuidSessionIdGenerator();
	}
	
	/**
	 * 配置会话Cookie模板
	 */
	@Bean
	public SimpleCookie sessionIdCookie() {
		//这里相当于配置：<constructor-arg value="sid"/>
		SimpleCookie simpleCookie = new SimpleCookie("sid");
		simpleCookie.setValue("sid");
		simpleCookie.setHttpOnly(true);
		simpleCookie.setMaxAge(180000);
		return simpleCookie;
	}
	
	/**
	 * 配置会话DAO
	 */
	@Bean
	public EnterpriseCacheSessionDAO sessionDAO(JavaUuidSessionIdGenerator sessionIdGenerator) {
		EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
		enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator);
		return enterpriseCacheSessionDAO;
	}
	
	/**
	 * 配置会话验证调度器
	 * 注意：这里使用QuartzSessionValidationScheduler的话log4j2会报空指针异常。
	 *      估计是因为没有配置seesionManager，但这样配置会导致循环注入，暂时没有解决的方法。
	 * 这里改用ExecutorServiceSessionValidationScheduler配置调度器可暂时解决问题
	 * 参考：http://www.60kb.com/post/15.html
	 */
/*	@Bean
	public QuartzSessionValidationScheduler sessionValidationScheduler() {
		QuartzSessionValidationScheduler quartzSessionValidationScheduler = new QuartzSessionValidationScheduler();
		quartzSessionValidationScheduler.setSessionValidationInterval(1800000);
		//quartzSessionValidationScheduler.setSessionManager(sessionManager);
		return quartzSessionValidationScheduler;
	}*/
	@Bean(name = "sessionValidationScheduler")
	public ExecutorServiceSessionValidationScheduler sessionValidationScheduler() {
		ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
		scheduler.setInterval(1800000);
		return scheduler;
	}
	
	/**
	 * 配置会话管理器
	 */
	@Bean
	public DefaultWebSessionManager sessionManager(
			ExecutorServiceSessionValidationScheduler sessionValidationScheduler,
			EnterpriseCacheSessionDAO sessionDAO,
			SimpleCookie sessionIdCookie) {
		DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
		defaultWebSessionManager.setGlobalSessionTimeout(1800000);
		defaultWebSessionManager.setDeleteInvalidSessions(true);
		defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
		defaultWebSessionManager.setSessionValidationScheduler(sessionValidationScheduler);
		defaultWebSessionManager.setSessionDAO(sessionDAO);
		defaultWebSessionManager.setSessionIdCookieEnabled(true);
		defaultWebSessionManager.setSessionIdCookie(sessionIdCookie);
		return defaultWebSessionManager;
	}
	
	/**
	 * 配置安全管理器
	 */
	@Bean
	public DefaultWebSecurityManager securityManager(
			UserRealm userRealm,
			DefaultWebSessionManager sessionManager,
			EhCacheManager cacheManager) {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(userRealm);
		defaultWebSecurityManager.setSessionManager(sessionManager);
		defaultWebSecurityManager.setCacheManager(cacheManager);
		return defaultWebSecurityManager;
	}
	
	/**
	 * 相当于调用SecurityUtils.setSecurityManager(securityManager)
	 */
	@Bean
	public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		methodInvokingFactoryBean.setArguments(securityManager);
		return methodInvokingFactoryBean;
	}
	
	/**
	 * 基于Form表单的身份验证过滤器
	 */
	@Bean
	public FormAuthenticationFilter formAuthenticationFilter() {
		FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
		formAuthenticationFilter.setUsernameParam("username");
		formAuthenticationFilter.setPasswordParam("password");
		formAuthenticationFilter.setLoginUrl("/test/login");
		return formAuthenticationFilter;
	}
	
	/**
	 * 配置Shiro的Web过滤器
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(
			DefaultWebSecurityManager securityManager,
			FormAuthenticationFilter formAuthenticationFilter) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/test/login");
		shiroFilterFactoryBean.setUnauthorizedUrl("/test/unauthorized");
		HashMap<String, Filter> filters = new HashMap<String, Filter>();
		filters.put("authc", formAuthenticationFilter);
		shiroFilterFactoryBean.setFilters(filters);
		Map<String, String> chainMap = new LinkedHashMap<String, String>();
		chainMap.put("/test/index", "anon");
		chainMap.put("/test/unauthorized", "anon");
		chainMap.put("/test/login", "authc");
		chainMap.put("/logout", "logout");
		chainMap.put("/**", "user");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(chainMap);
		return shiroFilterFactoryBean;
	}
	
	/**
	 * 配置Shiro生命周期处理器
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
}
