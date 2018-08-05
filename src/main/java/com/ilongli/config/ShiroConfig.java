package com.ilongli.config;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ilongli.shiro.credentials.RetryLimitHashedCredentialsMatcher;

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
}
