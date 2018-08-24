package com.ilongli.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ilongli.service.UserService;
import com.ilongli.shiro.MySessionDAO;
import com.ilongli.shiro.credentials.RetryLimitHashedCredentialsMatcher;
import com.ilongli.shiro.filter.JCaptchaValidateFilter;
import com.ilongli.shiro.filter.MyFormAuthenticationFilter;
import com.ilongli.shiro.filter.SysUserFilter;
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
		userRealm.setCredentialsMatcher(credentialsMatcher);
		userRealm.setCachingEnabled(false);
		
		/** 这里无需再做登录认证和身份认证的缓存，因为druid已经对所有的sql语句做了缓存处理 **/
/*		userRealm.setAuthenticationCachingEnabled(true);
		userRealm.setAuthenticationCacheName("authenticationCache");
		userRealm.setAuthorizationCachingEnabled(true);
		userRealm.setAuthorizationCacheName("authorizationCache");*/
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
	 * 配置session会话Cookie
	 */
	@Bean
	public SimpleCookie sessionIdCookie() {
		//这里相当于配置：<constructor-arg value="sid"/>
		SimpleCookie simpleCookie = new SimpleCookie("sid");
//		simpleCookie.setDomain(".c.com");
		simpleCookie.setPath("/");	//path设置为 '/' 用于多个系统共享sid
		simpleCookie.setHttpOnly(true);
		simpleCookie.setMaxAge(-1);	//-1表示浏览器关闭时失效此Cookie
		return simpleCookie;
	}
	
	/**
	 * 配置记住我功能Cookie
	 */
	@Bean
	public SimpleCookie rememberMeCookie() {
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//		simpleCookie.setDomain(".c.com");
		simpleCookie.setPath("/");	//path设置为 '/' 用于多个系统共享rememberMe
		simpleCookie.setHttpOnly(true);
		simpleCookie.setMaxAge(864000);	//10天
		return simpleCookie;
	}
	
	/**
	 * rememberMe管理器
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		cookieRememberMeManager.setCookie(rememberMeCookie);
		return cookieRememberMeManager;
	}
	
	/**
	 * 配置session会话DAO
	 */
/*	@Bean
	public EnterpriseCacheSessionDAO sessionDAO(JavaUuidSessionIdGenerator sessionIdGenerator) {
		EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
		enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator);
		return enterpriseCacheSessionDAO;
	}*/
	@Bean
	public MySessionDAO sessionDAO(JavaUuidSessionIdGenerator sessionIdGenerator) {
		MySessionDAO sessionDAO = new MySessionDAO();
//		sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		sessionDAO.setSessionIdGenerator(sessionIdGenerator);
		return sessionDAO;
	}
	
	/**
	 * 配置会话验证调度器
	 * 注意：这里使用QuartzSessionValidationScheduler的话log4j2会报空指针异常。
	 *      估计是因为没有配置seesionManager，但这样配置会导致循环注入，暂时没有解决的方法。
	 * 这里改用ExecutorServiceSessionValidationScheduler配置调度器可暂时解决问题
	 * 参考：http://www.60kb.com/post/15.html
	 */
/*	@Bean
	public QuartzSessionValidationScheduler sessionValidationScheduler(DefaultWebSessionManager sessionManager) {
		QuartzSessionValidationScheduler quartzSessionValidationScheduler = new QuartzSessionValidationScheduler();
		quartzSessionValidationScheduler.setSessionValidationInterval(1800000);
		quartzSessionValidationScheduler.setSessionManager(sessionManager);
		return quartzSessionValidationScheduler;
	}*/
	
 	@Bean
	public ExecutorServiceSessionValidationScheduler sessionValidationScheduler() {
		ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
		scheduler.setInterval(1800000);
		return scheduler;
	}
	
	/**
	 * 配置会话管理器
	 */
	/**
	 * 采用shiro自带的DefaultWebSessionManager作为session管理器
	 * 以后考虑直接将session存入redis进行管理
	 *	注意，这里配置了sessionManager，并且在securityManager注入后，
	 * 	因为@WebFilter无法确定顺序，因此配置的webFilter无法确保在shiroFilter的后面，
	 * 	无法确保webFilter的request是被shiroFilter所封装过的，即在webFilter获取的session
	 * 	无法确保是该sessionManager所生成的。
	 *有两种方法解决：
	 * 	1、通过Controller获取验证码，因为能够确保进入控制器方法的request是经shiro封装过的。
	 * 	2、给所有过滤器加上"Filter[x]"，x为编号，因为@WebFilter所定义的多个过滤器是根据类名的自然
	 * 		顺序执行的，因此只要给shiro的过滤器类起名如"Filter0_ShiroFilter"，而其他过滤器则从
	 * 		"Filter1_xxx"起名，就能确保shiro的过滤器在其他过滤器之前所执行，自然传入其他过滤器的
	 * 		request是被shiro所封装过的。
	 * 		参考：https://blog.csdn.net/liming_0820/article/details/53332070
	 */
	@Bean
	public DefaultWebSessionManager sessionManager(
			ExecutorServiceSessionValidationScheduler sessionValidationScheduler,
			MySessionDAO sessionDAO,
			SimpleCookie sessionIdCookie) {
		DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
		defaultWebSessionManager.setGlobalSessionTimeout(1800000);
		defaultWebSessionManager.setDeleteInvalidSessions(true);
		
		defaultWebSessionManager.setSessionIdUrlRewritingEnabled(false);
		
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
			EhCacheManager cacheManager,
			CookieRememberMeManager rememberMeManager,
			DefaultWebSessionManager sessionManager) {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(userRealm);
		defaultWebSecurityManager.setSessionManager(sessionManager);
		defaultWebSecurityManager.setCacheManager(cacheManager);
		defaultWebSecurityManager.setRememberMeManager(rememberMeManager);
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
	public MyFormAuthenticationFilter authcFilter() {
		MyFormAuthenticationFilter authcFilter = new MyFormAuthenticationFilter();
		authcFilter.setUsernameParam("username");
		authcFilter.setPasswordParam("password");
		//登录失败后存储到的Attribute属性名
		authcFilter.setFailureKeyAttribute("shiroLoginFailure");
		//设置rememberMe请求参数名
		authcFilter.setRememberMeParam("rememberMe");
		return authcFilter;
	}
	
	/**
	 * 系统用户过滤器，将登录的用户信息放入到session中
	 */
	@Bean
	public SysUserFilter sysUserFilter() {
		return new SysUserFilter();
	}
	
	/**
	 * 验证码过滤器
	 */
	@Bean
	public JCaptchaValidateFilter jCaptchaValidateFilter() {
		JCaptchaValidateFilter jCaptchaValidateFilter = new JCaptchaValidateFilter();
		jCaptchaValidateFilter.setJcaptchaEbabled(true);
		jCaptchaValidateFilter.setJcaptchaParam("jcaptchaCode");
		jCaptchaValidateFilter.setFailureKeyAttribute("shiroLoginFailure");
		return jCaptchaValidateFilter;
	}
	
	/**
	 * 配置Shiro的Web过滤器
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(
			DefaultWebSecurityManager securityManager,
			MyFormAuthenticationFilter authcFilter,
			SysUserFilter sysUserFilter,
			JCaptchaValidateFilter jCaptchaValidateFilter) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
		shiroFilterFactoryBean.setSuccessUrl("/");
		
		HashMap<String, Filter> filters = new HashMap<String, Filter>();
		filters.put("authc", authcFilter);
		filters.put("sysUser", sysUserFilter);
		filters.put("jCaptchaValidate", jCaptchaValidateFilter);
		shiroFilterFactoryBean.setFilters(filters);
		
		Map<String, String> chainMap = new LinkedHashMap<String, String>();
		//anon表示无需验证
		chainMap.put("/static/**", "anon");
		chainMap.put("/favicon.ico", "anon");
		chainMap.put("/unauthorized", "anon");
		chainMap.put("/jcaptcha*", "anon");
		//authc表示访问该地址用户必须身份验证通过[Subject.isAuthenticated()==true]
		chainMap.put("/login", "jCaptchaValidate,authc");
		chainMap.put("/test/testfm", "authc");	
		chainMap.put("/logout", "logout");
		//user表示访问该地址的用户是身份验证通过或 RememberMe登录的都可以
		chainMap.put("/**", "user,sysUser");
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
