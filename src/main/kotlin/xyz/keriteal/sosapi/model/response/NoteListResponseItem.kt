package xyz.keriteal.sosapi.model.response

import java.time.LocalDateTime

data class NoteListResponseItem(
    val noteId: Int,
    val noteCreatorId: Int?,
    val noteCreatorName: String?,
    val noteContent: String?,
    val noteCreateTime: LocalDateTime?,
    val isRecommend: Boolean
)