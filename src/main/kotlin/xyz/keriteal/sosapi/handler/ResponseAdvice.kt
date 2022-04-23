package xyz.keriteal.sosapi.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import xyz.keriteal.sosapi.SosException
import xyz.keriteal.sosapi.annotation.Logger
import xyz.keriteal.sosapi.annotation.Logger.Companion.logger
import xyz.keriteal.sosapi.config.ProfileProperties
import xyz.keriteal.sosapi.enum.ApiResult
import xyz.keriteal.sosapi.model.ApiModel
import xyz.keriteal.sosapi.model.Failed
import xyz.keriteal.sosapi.model.Success
import java.lang.reflect.Field

@Logger
@RestControllerAdvice
class ResponseAdvice @Autowired constructor(
    private val objectMapper: ObjectMapper,
    private val profileProperties: ProfileProperties
) : ResponseBodyAdvice<Any> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        if (profileProperties.development && body != null) {
            logger.debug("请求响应Body[${body::class.java}]:$body")
        }
        if (body is String) {
            return try {
                val map = objectMapper.readValue(body, Map::class.java)
                if (map.containsKey("openapi")) {
                    objectMapper.writeValueAsString(map)
                } else {
                    objectMapper.writeValueAsString(Success(map))
                }
            } catch (e: Exception) {
                response.headers.contentType = MediaType.APPLICATION_JSON
                objectMapper.writeValueAsString(Success(body))
            }
        }
        if (body is ApiModel) {
            if (body is Failed) {
                val result = body.apiResult ?: return body
                response.setStatusCode(HttpStatus.valueOf(result.httpCode))
            }
            return body
        }
        return Success(body)
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleArgumentNotValidException(ex: MethodArgumentNotValidException): ApiModel {
        val bindingResult = ex.bindingResult
        val errors = bindingResult.fieldErrors
        val errorMessages = HashMap<String, String>(errors.size)
        errors.forEachNotNull { error ->
            error.defaultMessage?.let {
                errorMessages[error.field] = it
            }
        }
        return Failed.fromErrors(errorMessages)
    }

    @ResponseBody
    @ExceptionHandler(SosException::class)
    fun sosException(e: SosException): ApiModel {
        if (e.result.log || profileProperties.development) {
            logger.error("捕获一个SOS异常: ${e.localizedMessage}", e)
        }
        return Failed.fromApiResult(e.result)
    }

    @ResponseBody
    @ExceptionHandler(Exception::class)
    fun exception(e: Exception): ApiModel {
        logger.error("捕获一个未知异常: ${e.localizedMessage}", e)
        return Failed.fromException(e)
    }

    private inline fun <T> Iterable<T>.forEachNotNull(action: (T) -> Unit): Unit {
        for (element in this) element?.let(action)
    }
}