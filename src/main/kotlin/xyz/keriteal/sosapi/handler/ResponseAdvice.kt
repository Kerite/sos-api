package xyz.keriteal.sosapi.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import xyz.keriteal.sosapi.annotation.Logger
import xyz.keriteal.sosapi.annotation.Logger.Companion.logger
import xyz.keriteal.sosapi.config.ProfileProperties
import xyz.keriteal.sosapi.exception.SosException
import xyz.keriteal.sosapi.model.ApiModel
import xyz.keriteal.sosapi.model.Failed
import xyz.keriteal.sosapi.model.Success
import xyz.keriteal.sosapi.repository.ApiLogRepository
import xyz.keriteal.sosapi.utils.getRequest

/**
 * 统一所有接口的返回格式
 * 可能会造成有些接口无法使用，需要配置 matchers
 * @author Kerite
 */
@Logger
@RestControllerAdvice
class ResponseAdvice @Autowired constructor(
    private val objectMapper: ObjectMapper,
    private val profileProperties: ProfileProperties,
    private val apiLogRepository: ApiLogRepository
) : ResponseBodyAdvice<Any> {
    val matchers = listOf(
        AntPathRequestMatcher("/actuator/**"),
        AntPathRequestMatcher("/v3/api-docs/**")
    )

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
        // 过滤不需要统一返回类型的接口Url
        matchers.forEach {
            if (it.matches(getRequest())) return body
        }
        // 过滤不需要统一返回类型的接口 ContentType
        if (selectedContentType == MediaType.APPLICATION_JSON)
            return body

        if (profileProperties.development && body != null) {
            logger.debug("请求响应Body[${body::class.java}]:$body")
        } else {
            logger.debug("请求响应为null")
        }
        if (body is String) {
            val map = objectMapper.readValue(body, Map::class.java)
            objectMapper.writeValueAsString(Success(map))
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

    /**
     * 捕获参数校验异常
     */
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

    /**
     * 捕获自定义的异常
     */
    @ResponseBody
    @ExceptionHandler(SosException::class)
    fun sosException(e: SosException): ApiModel {
        if (e.result.log || profileProperties.development) {
            logger.error("捕获一个SOS异常: ${e.localizedMessage}", e)
        }
        return Failed.fromApiResult(e.result)
    }

    /**
     * 捕获所有异常（漏网之鱼）
     */
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