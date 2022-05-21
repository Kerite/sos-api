package xyz.keriteal.sosapi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import xyz.keriteal.sosapi.annotation.ApiCallLog
import xyz.keriteal.sosapi.model.request.AddRolesRequest
import xyz.keriteal.sosapi.model.request.LoginRequest
import xyz.keriteal.sosapi.model.request.RegisterRequest
import xyz.keriteal.sosapi.model.request.RolesRequest
import xyz.keriteal.sosapi.model.response.CaptchaResponse
import xyz.keriteal.sosapi.model.response.LoginResponse
import xyz.keriteal.sosapi.model.response.RegisterResponse
import xyz.keriteal.sosapi.model.response.RolesResponseItem
import xyz.keriteal.sosapi.service.AuthService
import xyz.keriteal.sosapi.service.RoleService

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(
    private val authService: AuthService,
    private val roleService: RoleService,
) {
    @GetMapping("public_key")
    fun publicKey(
        @RequestParam appKey: String,
        @RequestParam appSecret: String
    ) {

    }

    @PostMapping("login")
    @ApiCallLog(tag = "login")
    fun login(@RequestBody request: LoginRequest): LoginResponse {
        return authService.login(request)
    }

    @PostMapping("register")
    @Operation(method = "POST", description = "注册用户")
    fun register(@RequestBody request: RegisterRequest): RegisterResponse {
        authService.register(request)
        return RegisterResponse("OK")
    }

    @GetMapping("roles")
    @Operation(method = "GET", description = "获取用户的所有权限")
    fun roles(request: RolesRequest): List<RolesResponseItem> {
        return roleService.listAllRoles(request.userId)
    }

    @PostMapping("roles")
    @Operation(method = "POST", description = "给用户添加权限")
    fun addRole(request: AddRolesRequest): Boolean {
        return roleService.addRoles(request.userId, request.roleIds)
    }

    @GetMapping("captcha")
    @Operation(method = "GET", description = "获取验证码")
    fun captcha(): CaptchaResponse {
        return authService.generateCaptcha()
    }
}