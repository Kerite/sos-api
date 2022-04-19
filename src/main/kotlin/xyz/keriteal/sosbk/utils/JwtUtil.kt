package xyz.keriteal.sosbk.utils

import io.github.nefilim.kjwt.*
import io.github.nefilim.kjwt.ClaimsVerification.expired
import io.github.nefilim.kjwt.ClaimsVerification.notBefore
import io.github.nefilim.kjwt.ClaimsVerification.requiredOptionClaim
import io.github.nefilim.kjwt.ClaimsVerification.validateClaims
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import xyz.keriteal.sosbk.model.ApplicationEntity
import xyz.keriteal.sosbk.model.JwtModel
import java.time.LocalDateTime
import java.util.UUID

object JwtUtil {
    const val JWT_HEADER = "Bearer "
    const val CLAIM_USERNAME = "username"
    const val CLAIM_SESSION = "session"
    const val CLAIM_APP_KEY = "app_key"

    object JwtValidation {
        fun standardValidation(claims: JWTClaims): ClaimsValidatorResult =
            validateClaims(notBefore, expired)(claims)

        fun standardValidationWithUsername(claims: JWTClaims, username: String): ClaimsValidatorResult =
            validateClaims(notBefore, expired, username(username))(claims)

        private fun username(username: String): ClaimsValidator = requiredOptionClaim(
            "username",
            { claimValue("username") },
            { it == username }
        )
    }

    fun getUsernameFromToken(token: String): String? {
        return JWT.decode(token).orNull()
            ?.claimValue(CLAIM_USERNAME)?.orNull()
    }

    fun createAndSignJwt(jwtModel: JwtModel, application: ApplicationEntity): String? {
        return JWT.hs256("kid-233".toJWTKeyID()) {
            issuer(application.appName!!)
            claim(CLAIM_USERNAME, jwtModel.username)
            claim(CLAIM_SESSION, UUID.randomUUID().toString())
            claim(CLAIM_APP_KEY, application.appKey!!)
            issuedNow()
        }.sign(application.appJwtKey!!).orNull()?.jwt?.encode()
    }
}

