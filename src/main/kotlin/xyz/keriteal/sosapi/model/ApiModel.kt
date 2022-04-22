package xyz.keriteal.sosapi.model

import xyz.keriteal.sosapi.enum.ApiResult

@Suppress("unused")
sealed class ApiModel {
    abstract val code: Int
    abstract val message: String
    val timestamp: Long = System.currentTimeMillis()
}

data class Success<T>(
    val data: T,
    override val code: Int = 0,
    override val message: String = ""
) : ApiModel()

data class Failed(
    override val code: Int = 500,
    override val message: String = ""
) : ApiModel() {
    companion object {
        fun fromApiResult(result: ApiResult): Failed {
            return Failed(code = ApiResult.RC500.code, message = ApiResult.RC500.message)
        }
    }
}