package com.ilongli.config;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
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
	 * 凭证匹配器
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
		return null;
	}
	
	/**
	 * 配置会话验证调度器
	 */
	@Bean
	public QuartzSessionValidationScheduler sessionValidationScheduler(DefaultWebSessionManager sessionManager) {
		QuartzSessionValidationScheduler quartzSessionValidationScheduler = new QuartzSessionValidationScheduler();
		quartzSessionValidationScheduler.setSessionValidationInterval(1800000);
		quartzSessionValidationScheduler.setSessionManager(sessionManager);
		return quartzSessionValidationScheduler;
	}
	
	/**
	 * 配置会话管理器
	 */
	@Bean
	public DefaultWebSessionManager sessionManager(
			QuartzSessionValidationScheduler sessionValidationScheduler,
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
		formAuthenticationFilter.setLoginUrl("/login.jsp");
		return formAuthenticationFilter;
	}
	
	/**
	 * Shiro的Web过滤器
	 */
	
	
}
