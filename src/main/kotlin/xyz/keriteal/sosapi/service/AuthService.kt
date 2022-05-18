package xyz.keriteal.sosapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.keriteal.sosapi.annotation.Logger
import xyz.keriteal.sosapi.annotation.Logger.Companion.logger
import xyz.keriteal.sosapi.constants.ParameterConstants
import xyz.keriteal.sosapi.constants.ParameterConstants.ALLOW_REGISTER
import xyz.keriteal.sosapi.constants.ParameterConstants.PASSWORD_REGEX
import xyz.keriteal.sosapi.entity.UserEntity
import xyz.keriteal.sosapi.enum.ApiResult
import xyz.keriteal.sosapi.exception.SosException
import xyz.keriteal.sosapi.model.JwtModel
import xyz.keriteal.sosapi.model.request.LoginRequest
import xyz.keriteal.sosapi.model.request.RegisterRequest
import xyz.keriteal.sosapi.model.response.LoginResponse
import xyz.keriteal.sosapi.repository.ApplicationRepository
import xyz.keriteal.sosapi.repository.OrganizationRepository
import xyz.keriteal.sosapi.repository.UserRepository
import xyz.keriteal.sosapi.utils.JwtUtil

@Service
@Logger
class AuthService @Autowired constructor(
    private val applicationRepository: ApplicationRepository,
    private val userRepository: UserRepository,
    private val parameterService: ParameterService,
    private val appService: ApplicationService,
    private val passwordEncoder: PasswordEncoder,
    private val organizationRepository: OrganizationRepository
) {
    fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByUsername(request.username)
            ?: throw SosException(ApiResult.USERNAME_NOT_FOUND)
        val jwtModel = JwtModel(username = user.username)
        val application = applicationRepository.findByAppKey(request.appKey)
            ?: throw SosException(ApiResult.APPLICATION_NOT_FOUND)
        val jwt = JwtUtil.createAndSignJwt(jwtModel, application)
            ?: throw SosException(ApiResult.JWT_SIGN_FAILED)
        logger.debug("Jwt签名：$jwtModel")
        return LoginResponse(jwt = jwt)
    }

    @Transactional
    fun register(request: RegisterRequest) {
        val allowRegister = parameterService.getAppParameter(
            ALLOW_REGISTER, "true"
        )
        if (allowRegister != "true") {
            throw SosException(ApiResult.REGISTER_NOT_ALLOWED)
        }
        if (userRepository.existsByUsername(request.username)) {
            throw SosException(ApiResult.CONFLICT_USER)
        }
        val orgs = request.organizations
        val orgEntities = organizationRepository.findAllById(orgs)
        // Save User
        val user = userRepository.save(
            UserEntity(
                username = request.username,
                password = passwordEncoder.encode(request.password),
                organizations = orgEntities.toMutableSet()
            )
        )
        if (validatePassword(request.password)) {
            throw SosException(ApiResult.PASSWORD_INVALID)
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