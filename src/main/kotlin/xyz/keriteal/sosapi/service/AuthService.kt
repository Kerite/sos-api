package xyz.keriteal.sosapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.keriteal.sosapi.annotation.Logger
import xyz.keriteal.sosapi.annotation.Logger.Companion.logger
import xyz.keriteal.sosapi.constants.Constants
import xyz.keriteal.sosapi.constants.ParameterConstants
import xyz.keriteal.sosapi.constants.ParameterConstants.ALLOW_REGISTER
import xyz.keriteal.sosapi.constants.ParameterConstants.PASSWORD_REGEX
import xyz.keriteal.sosapi.entity.UserEntity
import xyz.keriteal.sosapi.enums.ApiResult
import xyz.keriteal.sosapi.exception.SosException
import xyz.keriteal.sosapi.model.JwtModel
import xyz.keriteal.sosapi.model.SosUser
import xyz.keriteal.sosapi.model.request.LoginRequest
import xyz.keriteal.sosapi.model.request.RegisterRequest
import xyz.keriteal.sosapi.model.response.CaptchaResponse
import xyz.keriteal.sosapi.model.response.LoginResponse
import xyz.keriteal.sosapi.repository.ApplicationRepository
import xyz.keriteal.sosapi.repository.OrganizationRepository
import xyz.keriteal.sosapi.repository.UserRepository
import xyz.keriteal.sosapi.service.captcha.CaptchaServiceFactory
import xyz.keriteal.sosapi.utils.JwtUtil
import java.util.*

@Service
@Logger
class AuthService @Autowired constructor(
    private val applicationRepository: ApplicationRepository,
    private val userRepository: UserRepository,
    private val parameterService: ParameterService,
    private val appService: ApplicationService,
    private val passwordEncoder: PasswordEncoder,
    private val organizationRepository: OrganizationRepository,
    private val captchaServiceFactory: CaptchaServiceFactory,
    private val redisService: RedisService
) {
    /**
     * 登录
     */
    fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByUsername(request.username)
            ?: throw SosException(ApiResult.USERNAME_NOT_FOUND)
        val jwtModel = JwtModel(username = user.username)
        val application = applicationRepository.findByKey(request.appKey)
            ?: throw SosException(ApiResult.APPLICATION_NOT_FOUND)
        val jwt = JwtUtil.createAndSignJwt(jwtModel, application)
            ?: throw SosException(ApiResult.JWT_SIGN_FAILED)
        logger.debug("Jwt签名：$jwtModel")
        return LoginResponse(jwt = jwt)
    }

    /**
     * 注册用户
     */
    @Transactional
    fun register(request: RegisterRequest): Boolean {
        val allowRegister = parameterService.getAppParameter(
            ALLOW_REGISTER, "true"
        )
        if (allowRegister != "true") {
            throw SosException(ApiResult.REGISTER_NOT_ALLOWED)
        }
        if (userRepository.existsByUsername(request.username)) {
            throw SosException(ApiResult.CONFLICT_USER)
        }
        val organizations = request.organizations
        val orgEntities = organizationRepository.findAllById(organizations)
        // 保存用户
        userRepository.save(
            UserEntity(
                username = request.username,
                password = passwordEncoder.encode(request.password),
                organizations = orgEntities.toMutableSet()
            )
        )
        if (validatePassword(request.password)) {
            throw SosException(ApiResult.PASSWORD_INVALID)
        }
        return true
    }

    /**
     * 生成验证码并且保存到redis中缓存
     * 1. 生成UUID作为登录的Session
     */
    fun generateCaptcha(): CaptchaResponse {
        val captchaType = parameterService.getAppParameter(ParameterConstants.CAPTCHA_TYPE, "default");
        val service = captchaServiceFactory.getService(captchaType)
        val sessionId = UUID.randomUUID().toString()
        val captcha = service.generateCaptcha()
        redisService.putObject(
            arrayOf(Constants.REDIS_KEY_PREFIX, "auth", "captcha", sessionId).joinToString(
                Constants.REDIS_KEY_SEPARATOR
            ),
            captcha
        )
        return CaptchaResponse(
            captchaData = captcha.captchaData,
            captchaSession = sessionId
        )
    }

    fun getLoginUser(): SosUser {
        try {
            val authentication = SecurityContextHolder
                .getContext().authentication
            return authentication.principal as SosUser
        } catch (e: Exception) {
            throw SosException(ApiResult.RC401)
        }
    }

    /**
     * 验证密码是否可用
     */
    private fun validatePassword(password: String): Boolean {
        if (password.isBlank()) {
            throw SosException(ApiResult.PASSWORD_MISSING)
        }
        val regExp = parameterService.getAppParameter(PASSWORD_REGEX, ParameterConstants.DEFAULT_PASSWORD_REGEX)
        val regex = Regex(regExp)
        return regex.matches(password)
    }
}