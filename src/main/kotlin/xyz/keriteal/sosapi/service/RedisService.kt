package xyz.keriteal.sosapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import xyz.keriteal.sosapi.annotation.Logger
import xyz.keriteal.sosapi.annotation.Logger.Companion.logger
import xyz.keriteal.sosapi.config.ProfileConfig
import java.time.Duration

@Service
@Logger
class RedisService @Autowired constructor(
    private val redisTemplate: RedisTemplate<String, out Any>,
    private val profileConfig: ProfileConfig
) {
    fun expire(key: String, time: Duration): Boolean {
        try {
            redisTemplate.expire(key, time)
            return true
        } catch (e: Exception) {
            logger.error("Redis Error", e)
            return false
        }
    }

    fun getExpire(key: String): Long {
        return redisTemplate.getExpire(key);
    }

    fun delete(vararg keys: String) {
        redisTemplate.delete(keys.asList())
    }

    fun <T> getObject(key: String): T? {
        return redisTemplate.opsForValue().get(key) as T
    }

    fun <T> putObject(key: String, value: T, expire: Duration? = null) {
        if (expire != null) {
            redisTemplate.opsForValue().set(key, value as Nothing, expire)
        } else {
            redisTemplate.opsForValue().set(key, value as Nothing)
        }
    }
}