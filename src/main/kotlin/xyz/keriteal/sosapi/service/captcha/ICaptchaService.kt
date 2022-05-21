package xyz.keriteal.sosapi.service.captcha

import xyz.keriteal.sosapi.dto.CaptchaOutput

interface ICaptchaService {
    fun getCaptchaType(): String
    fun generateCaptcha(): CaptchaOutput
}