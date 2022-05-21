package xyz.keriteal.sosapi.model.response

data class CaptchaResponse(
    val captchaData: String,
    val captchaSession: String
)