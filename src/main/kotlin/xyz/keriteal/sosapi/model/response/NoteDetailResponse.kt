package xyz.keriteal.sosapi.model.response

import java.time.LocalDateTime

data class NoteDetailResponse(
    val noteId: Int,
    val noteCreatorId: Int,
    val noteCreatorName: String,
    val noteContent: String,
    val createTime: LocalDateTime?,
    val updateTime: LocalDateTime?
)