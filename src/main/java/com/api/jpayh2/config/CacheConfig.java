package com.api.jpayh2.config;

import java.util.HashMap;
import java.util.Map;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
public class CacheConfig {

    // administrador de cache
    @Bean
    public CacheManager getManager(RedissonClient redissonClient) {
        
        Map<String, CacheConfig> config = new HashMap<>();
        
        // users = es la coleccion donde va a trabajar
        config.put("users", new CacheConfig());
        
        return new RedissonSpringCacheManager(redissonClient);
        
    }
    
    
    
    //Agregar bean al contexto de spirng :
    @Bean(destroyMethod ="shutdown")
    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().
        setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }
}
