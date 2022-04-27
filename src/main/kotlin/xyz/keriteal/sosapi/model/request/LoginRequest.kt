package xyz.keriteal.sosapi.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import xyz.keriteal.sosapi.model.RequestModel
import javax.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank(message = "app_key不能为空")
    @field:JsonProperty("app_key")
    var appKey: String = "",
    @field:NotBlank(message = "用户名不能为空")
    var username: String,
    @field:NotBlank(message = "密码不能为空")
    var password: String
) : RequestModel()