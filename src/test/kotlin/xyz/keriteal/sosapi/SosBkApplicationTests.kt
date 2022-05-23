package xyz.keriteal.sosapi

import io.github.nefilim.kjwt.JWSHMAC256Algorithm
import io.github.nefilim.kjwt.JWT
import io.github.nefilim.kjwt.verifySignature
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import xyz.keriteal.sosapi.entity.ApplicationEntity
import xyz.keriteal.sosapi.enums.ApiResult
import xyz.keriteal.sosapi.model.JwtModel
import xyz.keriteal.sosapi.utils.JwtUtil
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

    @Test
    fun `test enum`() {
        println(ApiResult.APP_KEY_MISSING.name)
    }

    @Test
    fun `test kjwt`() {
        val key = "abc233466"
        val username = "kerit"
        val application = ApplicationEntity(
            code = "web",
            name = "web",
            key = "303e73aec1f74d81b74b",
            secret = "788b02124330d",
            jwtKey = key
        )
        val jwtModel = JwtModel(
            username = username
        )
        val jwt = JwtUtil.createAndSignJwt(jwtModel, application)
        val result = validateWithTokenHs256(username, jwt!!, key)
        println("jwt:$jwt,result:$result")
    }

    private fun validateWithTokenHs256(username: String, token: String, key: String): Boolean {
        try {
            val decodedJWT = JWT.decodeT(token, JWSHMAC256Algorithm).orNull()
                ?: return false
            val jwt = verifySignature(decodedJWT, key).orNull()
                ?: return false
            return JwtUtil.JwtValidation.standardValidationWithUsername(jwt, username).isValid
        } catch (e: Exception) {
            return false
        }
    }
}