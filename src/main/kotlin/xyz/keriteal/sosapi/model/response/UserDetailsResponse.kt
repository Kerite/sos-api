package xyz.keriteal.sosapi.model.response

import java.time.LocalDateTime

data class UserDetailsResponse(
    val userId: Int,
    val username: String,
    val joinTime: LocalDateTime,
    val updateTime: LocalDateTime,
    val datetime: LocalDateTime = LocalDateTime.now()
)