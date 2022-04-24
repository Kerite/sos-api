package xyz.keriteal.sosapi.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import xyz.keriteal.sosapi.model.request.LoginRequest
import xyz.keriteal.sosapi.model.request.RegisterRequest
import xyz.keriteal.sosapi.model.response.LoginResponse
import xyz.keriteal.sosapi.service.AuthService
import javax.validation.Valid

@Controller
@RequestMapping("/auth")
class AuthController @Autowired constructor(
    private val authService: AuthService
) {
    @GetMapping("public_key")
    fun publiKey(
        @RequestParam appKey: String,
        @RequestParam appSecret: String
    ) {
    }

    @PostMapping("login")
    @ResponseBody
    fun login(@Valid @RequestBody request: LoginRequest): LoginResponse {
        return authService.login(request)
    }

    @PostMapping("register")
    @ResponseBody
    fun register(@Valid @RequestBody request: RegisterRequest) {
        return authService.register(request)
    }
}