package xyz.keriteal.sosapi.model.request

import xyz.keriteal.sosapi.model.PageableRequest

data class GetQuestionsRequest(
    val type: String,
    val subject: String,
) : PageableRequest()