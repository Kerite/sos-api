package xyz.keriteal.sosapi.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.codec.digest.Md5Crypt
import org.apache.http.entity.ContentType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils
import org.springframework.web.filter.OncePerRequestFilter
import xyz.keriteal.sosapi.SosException
import xyz.keriteal.sosapi.config.ProfileProperties
import xyz.keriteal.sosapi.enum.ApiResult
import xyz.keriteal.sosapi.repository.ApplicationRepository
import xyz.keriteal.sosapi.utils.MultiplexRequestWrapper
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import javax.servlet.http.HttpServletResponse

/**
 * 本过滤器对以下过程进行校验：
 * 1 对所有url的参数进行排序
 * 2 在url的最后直接拼接上url参数中app_key对应的app_secret
 * 3 对第二步获取的字符串进行md5加密最后得到sign加入url的参数中
 */
@Component
class SignValidationFilter @Autowired constructor(
    private val applicationRepository: ApplicationRepository,
    private val profileProperties: ProfileProperties,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    companion object {
        const val SIGN_KEY_SIGN = "sign"
        const val SIGN_KEY_APP_KEY = "app_key"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (!profileProperties.needSign) {
            filterChain.doFilter(request, response)
        } else {
            val parameterMap = mapOf<String, List<String>>()
            val sign = parameterMap[SIGN_KEY_SIGN]
            if (sign.isNullOrEmpty()) {
                throw SosException(ApiResult.SIGN_MISSING)
            }
            val trimmedMap = parameterMap.filter { (key, _) ->
                key != SIGN_KEY_SIGN
            }
            val appKey = (parameterMap[SIGN_KEY_APP_KEY]
                ?: throw SosException(ApiResult.APP_KEY_MISSING))[0]
            val paramStr = trimmedMap.toList().map { (key, valueArray) ->
                return@map if (valueArray.size == 1) {
                    "$key=${valueArray[0]}"
                } else {
                    valueArray.joinToString("&") {
                        "$key[]=$it"
                    }
                }
            }.joinToString("&")
            val application = applicationRepository.findByAppKey(appKey)
                ?: throw SosException(ApiResult.APP_KEY_NOT_FOUND)
            val queryWithSign = paramStr + application.appSecret
            if (!Md5Crypt.md5Crypt(queryWithSign.toByteArray()).equals(sign[0])) {
                throw SosException(ApiResult.SIGN_INVALID)
            }
            filterChain.doFilter(request, response)
        }
    }

    private fun parseParameterList(request: HttpServletRequest): List<String>? {
        when (request.method.uppercase()) {
            "GET" -> {
                return request.parameterMap.toList().map { (key, valueArray) ->
                    return@map if (valueArray.size == 1) {
                        "$key=${valueArray[0]}"
                    } else {
                        valueArray.joinToString("&") {
                            "$key[]=$it"
                        }
                    }
                }
            }
            "POST" -> {
                val wrapper = MultiplexRequestWrapper(request)
                if (!request.contentType.contains(ContentType.APPLICATION_JSON.mimeType)) {
                    return null
                }
                val body = wrapper.body
                val obj = try {
                    objectMapper.readValue(body, Map::class.java)
                } catch (ex: Exception) {
                    return null
                }
                return obj.toList().map {
                    "${it.first}=${it.second}"
                }
            }
            else -> {
                return null
            }
        }
    }
}