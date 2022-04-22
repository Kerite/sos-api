package xyz.keriteal.sosapi.model.request

import xyz.keriteal.sosapi.model.RequestModel

class LoginRequest: RequestModel() {
    var username: String = ""
    var password: String = ""
}