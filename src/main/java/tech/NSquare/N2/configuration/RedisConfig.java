package tech.NSquare.N2.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {

//    @Value("{redis.timeout}")
//    private Integer redisTimeOut;
//
//    @Value("{redis.readFrom}")
//    private String redisFrom;
//
//    @Value("{redis.url}")
//    private String redisUrl;
//
//    @Value("{redis.port}")
//    private Integer redisPort;
//
//    @Bean
//    public RedisTemplate<String, String> redisTemplate() {
//
//        final JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setHostName(redisUrl);
//        factory.setPort(redisPort);
//        factory.setTimeout(redisTimeOut);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        StringRedisTemplate template = new StringRedisTemplate(factory);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setHashKeySerializer(jackson2JsonRedisSerializer);
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//        return template;
//    }

    @Bean
    public Cache<String, String> cacheTemplate() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                                ResourcePoolsBuilder.heap(100))
                                .build())
                .build(true);

        Cache<String, String> myCache
                = cacheManager.getCache("preConfigured", String.class, String.class);
        return myCache;
    }
}
