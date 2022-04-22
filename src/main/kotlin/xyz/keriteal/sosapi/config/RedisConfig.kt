package xyz.keriteal.sosapi.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@EnableCaching
class RedisConfig {
    @Bean
    open fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, out Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = factory
        redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }

    @Bean
    open fun redisCachingManager(factory: RedisConnectionFactory): RedisCacheManager {
        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    GenericJackson2JsonRedisSerializer()
                )
            ).serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    StringRedisSerializer()
                )
            )
        val redisExpireConfig = mutableMapOf<String, RedisCacheConfiguration>()
        redisExpireConfig["1min"] = RedisCacheConfiguration.defaultCacheConfig()
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    GenericJackson2JsonRedisSerializer()
                )
            ).serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    StringRedisSerializer()
                )
            ).entryTtl(Duration.ofMinutes(1)).disableCachingNullValues()
        return RedisCacheManager.builder(
            RedisCacheWriter.nonLockingRedisCacheWriter(factory)
        ).cacheDefaults(redisCacheConfiguration)
            .withInitialCacheConfigurations(redisExpireConfig)
            .transactionAware()
            .build()
    }
}