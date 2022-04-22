package xyz.keriteal.sosapi.model.request

import xyz.keriteal.sosapi.model.RequestModel

class RegisterRequest : RequestModel() {
    /**
     * 用户名
     */
    var username: String = ""

    /**
     * 加密后的密码
     */
    var password: String = ""

    /**
     * 机构列表
     */
    var organizations: List<Long> = listOf()
}