package xyz.keriteal.sosapi.constants

object ParameterConstants {
    const val ALLOW_REGISTER = "allow_register"
    const val PASSWORD_REGEX = "password_regex"
    const val DEFAULT_PASSWORD_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$"
}