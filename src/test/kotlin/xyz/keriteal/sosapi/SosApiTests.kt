package xyz.keriteal.sosapi

import io.github.nefilim.kjwt.*
import org.junit.jupiter.api.Test
import xyz.keriteal.sosapi.entity.ApplicationEntity
import xyz.keriteal.sosapi.model.JwtModel
import xyz.keriteal.sosapi.utils.JwtUtil
import io.github.nefilim.kjwt.JWT.Companion.hs256
import java.time.LocalDateTime

class SosApiTests {
    @Test
    fun `test kjwt`() {
        val key = "abc233466"
        val username = "kerit"
        val application = ApplicationEntity(
            appCode = "web",
            appName = "web",
            appKey = "303e73aec1f74d81b74b",
            appSecret = "788b02124330d",
            jwtKey = key
        )
        val jwtModel = JwtModel(
            username = username
        )
        val jwt = JwtUtil.createAndSignJwt(jwtModel, application)
        val result = validateWithTokenHs256(username, jwt!!, key)
        println("jwt:$jwt,result:$result")
    }

    @Test
    fun `test kjwwt2`() {
        val secret = "51sdf6a1f56sda4f4dfs6a4f"
        val jwt = hs256() {
            issuedAt(LocalDateTime.now())
            claim("username", "kerit")
        }.sign(secret).orNull()
        val token = jwt!!.rendered
        println("Rendered Token:$token")
        println("Signed Token: ${jwt.jwt.encode()}")
        val validated = verifySignature<JWSHMACAlgorithm>(jwt?.rendered!!, secret).orNull()
        val result = JwtUtil.JwtValidation.standardValidationWithUsername(validated!!, "kerit")
        println("$validated")
        println("result: ${result.isValid}")
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