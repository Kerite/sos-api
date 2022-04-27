package xyz.keriteal.sosapi.handler

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.nefilim.kjwt.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.filter.OncePerRequestFilter
import xyz.keriteal.sosapi.SosException
import xyz.keriteal.sosapi.annotation.Logger
import xyz.keriteal.sosapi.constants.UrlParamConstants.PARAM_APP_KEY
import xyz.keriteal.sosapi.enum.ApiResult
import xyz.keriteal.sosapi.repository.ApplicationRepository
import xyz.keriteal.sosapi.service.UserService
import xyz.keriteal.sosapi.utils.JwtUtil
import xyz.keriteal.sosapi.utils.JwtUtil.JWT_HEADER
import xyz.keriteal.sosapi.utils.MultiplexRequestWrapper
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Logger
@Component
class JwtAuthenticationTokenFilter @Autowired constructor(
    private val userDetailsService: UserService,
    private val applicationRepository: ApplicationRepository
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (!authHeader.isNullOrBlank() && authHeader.startsWith(JWT_HEADER)) {
            val tokenStr = authHeader.substring(JWT_HEADER.length)
            val username = JwtUtil.getUsernameFromToken(tokenStr)
            if (username.isNullOrBlank()) {
                logger.debug("Token中没有用户名")
            } else if (SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userDetailsService.loadUserByUsername(username)
//                httpServletRequest = MultiplexRequestWrapper(request)
                val appKey = JwtUtil.getAppKeyFromToken(tokenStr)
                    ?: throw SosException(ApiResult.APP_KEY_MISSING)
                val application = applicationRepository.findByAppKey(appKey)
                    ?: throw SosException(ApiResult.APPLICATION_NOT_FOUND)
                val appJwtKey = application.jwtKey
                // 验证jwt
                if (userDetails.validateWithTokenHs256(tokenStr, appJwtKey)) {
                    SecurityContextHolder.getContext().authentication = generateAuthenticationToken(
                        userDetails, request
                    )
                } else {
                    logger.debug("Jwt验证未通过:$tokenStr")
                }
            }
        }
        logger.debug("JwtAuthenticationTokenFilter结束")
        filterChain.doFilter(request, response)
    }

    fun generateAuthenticationToken(userDetails: UserDetails, request: HttpServletRequest)
    : Authentication {
        val authentication = UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.authorities
        )
        authentication.details = WebAuthenticationDetailsSource()
            .buildDetails(request)
        logger.debug("获取到用户信息:$authentication")
        return authentication
    }

    private fun UserDetails.validateWithTokenHs256(token: String, key: String): Boolean {
        try {
            val decodedJWT = JWT.decodeT(token, JWSHMAC256Algorithm).orNull()
                ?: return false
            val jwt = verifySignature(decodedJWT, key).orNull()
                ?: return false
            return JwtUtil.JwtValidation.standardValidationWithUsername(jwt, this.username).isValid
        } catch (e: Exception) {
            return false
        }
    }

    private fun UserDetails.validateWithTokenRsa256(token: String, pubkey: ByteArray): Boolean {
        try {
            val publicKey = KeyFactory.getInstance("RSA")
                .generatePublic(X509EncodedKeySpec(pubkey)) as RSAPublicKey
            val decodedJwt = JWT.decodeT(token, JWSRSA256Algorithm).orNull()
                ?: return false
            val jwt = verifySignature(decodedJwt, publicKey).orNull()
                ?: return false
            return JwtUtil.JwtValidation.standardValidationWithUsername(jwt, this.username).isValid
        } catch (e: Exception) {
            return false
        }
    }

    fun ifn(condition: Boolean, action: () -> Unit) {
        if (condition) action.invoke()
    }

    @Deprecated("现在使用token保存appKey")
    private fun extractAppKey(request: MultiplexRequestWrapper): String? {
        when (request.method.uppercase()) {
            "GET" -> {
                return request.parameterMap[PARAM_APP_KEY]?.get(0)
            }
            "POST" -> {
                if (!request.contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
                    return null
                }
                return jacksonObjectMapper().readValue(request.body, Map::class.java)[PARAM_APP_KEY] as String
            }
            else -> {
                return null
            }
        }
    }
}