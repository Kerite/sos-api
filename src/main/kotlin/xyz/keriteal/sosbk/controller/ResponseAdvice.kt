package xyz.keriteal.sosbk.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import xyz.keriteal.sosbk.SosException
import xyz.keriteal.sosbk.annotation.Logger
import xyz.keriteal.sosbk.annotation.Logger.Companion.logger
import xyz.keriteal.sosbk.enum.ApiResult
import xyz.keriteal.sosbk.model.ApiModel
import xyz.keriteal.sosbk.model.Failed
import xyz.keriteal.sosbk.model.Success

@RestControllerAdvice
@Logger
class ResponseAdvice @Autowired constructor(
    private val objectMapper: ObjectMapper
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
        if (body is String) {
            return objectMapper.writeValueAsString(Success(body))
        }
        return Success(body)
    }

    @ExceptionHandler(SosException::class)
    fun sosException(e: SosException): ApiModel {
        logger.error("捕获一个异常: ${e.localizedMessage}", e)
        return Failed.fromApiResult(e.result)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun exception(e: Exception): ApiModel {
        logger.error("捕获一个异常: ${e.localizedMessage}", e)
        return Failed.fromApiResult(ApiResult.RC500)
    }
}