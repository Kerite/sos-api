package xyz.keriteal.sosapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.web.servlet.NoHandlerFoundException
import xyz.keriteal.sosapi.enums.ApiResult
import xyz.keriteal.sosapi.exception.SosException

@Suppress("unused")
sealed class ApiModel {
    abstract val code: Int
    abstract val message: String
    val timestamp: Long = System.currentTimeMillis() / 1000
}

data class Success<T>(
    val data: T,
    override val code: Int = 200,
    override val message: String = "Success"
) : ApiModel()

data class Failed(
    override val code: Int,
    override val message: String = "",
    val errors: Map<String, String>? = null,
    @Transient
    @JsonIgnore
    val apiResult: ApiResult? = null
) : ApiModel() {
    companion object {
        fun fromApiResult(result: ApiResult): Failed {
            return Failed(code = result.code, message = result.message, apiResult = result)
        }

        fun fromSosException(e: SosException): Failed {
            return Failed(code = e.code, message = e.message)
        }

        fun fromException(e: Exception): Failed {
            if (e is NoHandlerFoundException) {
                return fromApiResult(ApiResult.RC404)
            }
            return Failed(code = 500, message = e.localizedMessage, apiResult = ApiResult.RC500)
        }

        fun fromErrors(errors: Map<String, String>): Failed {
            return Failed(400, "参数校验错误", errors, ApiResult.RC400)
        }
    }
}