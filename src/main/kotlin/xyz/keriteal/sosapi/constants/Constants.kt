package xyz.keriteal.sosapi.constants

object Constants {
    /**
     * 管理Actuator
     */
    const val AUTH_ACTUATOR_ADMIN = "ACTUATOR_ADMIN"
    const val AUTH_ROLE_ADMIN = "ROLE_ADMIN" //管理权限
    const val AUTH_ADD_NORMAL_USER = "ADD_NORMAL_USER" //添加用户(包括导入)

    const val APPLICATION_JSON = "application/json"

    const val REDIS_KEY_PREFIX = "sos"
    const val REDIS_KEY_SEPARATOR = "::"
}