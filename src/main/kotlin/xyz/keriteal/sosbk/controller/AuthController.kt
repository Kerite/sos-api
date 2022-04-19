package xyz.keriteal.sosbk.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/auth")
class AuthController {
    @GetMapping("public_key")
    fun publiKey(
        @RequestParam appKey: String,
        @RequestParam appSecret: String
    ) {

    }
}