package xyz.keriteal.sosapi.dto

data class UpdateUserInfoDto(
    val userId: Int,
    val username: String,
    val avatar: String
)