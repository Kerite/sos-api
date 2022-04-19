package xyz.keriteal.sosbk

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import javax.annotation.Resource

@SpringBootTest
class SosBkApplicationTests {
    @Resource
    lateinit var redisTemplate: RedisTemplate<String, Any>

    @Test
    fun `redis test`() {
        redisTemplate.opsForValue().set("test", "test")
        val result = redisTemplate.opsForValue().get("test")
        assert("test" == result)
    }
}