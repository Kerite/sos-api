package xyz.keriteal.sosapi.model.request

import io.swagger.v3.oas.annotations.media.SchemaProperty
import xyz.keriteal.sosapi.enums.EnumQuestionCreateType
import xyz.keriteal.sosapi.enums.EnumQuestionType

data class CreateQuestionsRequest(
    @SchemaProperty
    val description: String,
    val createMethod: EnumQuestionCreateType,
    val questionType: EnumQuestionType,
    val answer: String?,
)