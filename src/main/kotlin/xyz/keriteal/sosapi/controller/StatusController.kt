package xyz.keriteal.sosapi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.PermitAll

@RestController
@RequestMapping("/status")
class StatusController {
    @GetMapping("/ping")
    fun ping(): String {
        return "pong"
    }
}