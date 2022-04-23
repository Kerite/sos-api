package xyz.keriteal.sosapi.model.request

import xyz.keriteal.sosapi.model.RequestModel
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class RegisterRequest(
    /**
     * 用户名
     */
    @field: NotBlank(message = "用户名不能为空")
    @field: NotNull(message = "用户名不能为空")
    var username: String = "",

    /**
     * 加密后的密码
     */
    @field: NotBlank(message = "密码不能为空")
    @field: NotNull(message = "密码不能为空")
    var password: String = "",

    /**
     * 机构列表
     */
    var organizations: List<Long> = listOf()
) : RequestModel()