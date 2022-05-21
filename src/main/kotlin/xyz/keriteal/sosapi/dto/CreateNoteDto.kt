package xyz.keriteal.sosapi.dto

class CreateNoteDto(
    val creator: Int,
    val content: String,
    val courseId: Int,
    val lessonNum: Int
)