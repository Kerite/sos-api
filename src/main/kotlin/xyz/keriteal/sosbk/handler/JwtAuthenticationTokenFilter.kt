package xyz.keriteal.sosbk.handler

import io.github.nefilim.kjwt.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import xyz.keriteal.sosbk.annotation.Logger
import xyz.keriteal.sosbk.constants.ParamConstants
import xyz.keriteal.sosbk.constants.ParamConstants.PARAM_APP_KEY
import xyz.keriteal.sosbk.repository.ApplicationRepository
import xyz.keriteal.sosbk.service.UserDetailService
import xyz.keriteal.sosbk.utils.JwtUtil
import xyz.keriteal.sosbk.utils.JwtUtil.JWT_HEADER
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Logger
@Component
class JwtAuthenticationTokenFilter @Autowired constructor(
    private val userDetailsService: UserDetailService,
    private val applicationRepository: ApplicationRepository
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = response.getHeader(HttpHeaders.AUTHORIZATION)
        if (authHeader.isNotBlank() && authHeader.startsWith(JWT_HEADER)) {
            val authToken = authHeader.substring(JWT_HEADER.length)
            val username = JwtUtil.getUsernameFromToken(authToken)
            if (!username.isNullOrBlank() && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userDetailsService.loadUserByUsername(username)
                val appKey = request.parameterMap[PARAM_APP_KEY]?.get(0) ?: return
                val application = applicationRepository.findByAppKey(appKey)
                val appJwtKey = application?.appJwtKey ?: return
                if (userDetails.validateWithTokenHs256(authToken, appJwtKey)) {
                    val authentication = UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.authorities
                    )
                    authentication.details = WebAuthenticationDetailsSource()
                        .buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        }
        filterChain.doFilter(request, response)
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
}