package com.ilongli.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisPoolingClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置类
 * @author ilongli
 *
 */
@Configuration
@PropertySource(value = "classpath:properties/redis.properties")
public class RedisConfig {
	
    @Autowired
    Environment env;

	/**
	 * 配置jedisPool连接池
	 */
	@Bean
	public JedisPoolConfig poolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(env.getProperty("redis.maxTotal", Integer.class));
		jedisPoolConfig.setMaxIdle(env.getProperty("redis.maxIdle", Integer.class));
		jedisPoolConfig.setMaxWaitMillis(env.getProperty("redis.maxWaitMillis", Long.class));
		jedisPoolConfig.setTestOnBorrow(env.getProperty("redis.testOnBorrow", Boolean.class));
		jedisPoolConfig.setTestOnReturn(env.getProperty("redis.testOnReturn", Boolean.class));
		return jedisPoolConfig;
	}
	
	/**
	 * 配置jedis连接工厂
	 * 参考：
	 * https://blog.csdn.net/wangh92/article/details/80818318
	 * https://www.cnblogs.com/hetutu-5238/p/9212868.html
	 * https://blog.csdn.net/wobupeiai/article/details/81047547
	 */
	@Bean
	public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig poolConfig) {
		//redisStandaloneConfiguration
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(env.getProperty("redis.hostname", String.class));
		redisStandaloneConfiguration.setPort(env.getProperty("redis.port", Integer.class));
		redisStandaloneConfiguration.setDatabase(env.getProperty("redis.database", Integer.class));
		//clientConfiguration
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisClientConfiguration =
        		(JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        jedisClientConfiguration.poolConfig(poolConfig);
		return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration.build());
	}
	
	/**
	 * 配置redisTemplate模板
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
		
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        
		return template;
	}
}
