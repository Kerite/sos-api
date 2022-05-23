package xyz.keriteal.sosapi.exception

import xyz.keriteal.sosapi.enums.ApiResult

class SosException(
    val code: Int,
    override val message: String,
    val log: Boolean = false
) : RuntimeException(message) {
    constructor(result: ApiResult) : this(result.code, result.message)
}