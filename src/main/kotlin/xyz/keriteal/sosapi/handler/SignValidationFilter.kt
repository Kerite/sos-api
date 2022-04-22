package xyz.keriteal.sosapi.handler

import org.apache.commons.codec.digest.Md5Crypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import xyz.keriteal.sosapi.SosException
import xyz.keriteal.sosapi.config.ProfileProperties
import xyz.keriteal.sosapi.enum.ApiResult
import xyz.keriteal.sosapi.repository.ApplicationRepository
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
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
    @Qualifier("handlerExceptionResolver")
    private val resolver: HandlerExceptionResolver,
    private val profileProperties: ProfileProperties
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
            return
        }
        try {
            val parameterMap = request.parameterMap
            val sign = parameterMap[SIGN_KEY_SIGN]
            if (sign.isNullOrEmpty()) {
                throw SosException(ApiResult.SIGN_MISSING)
            }
            val trimedMap = parameterMap.filter { (key, _) ->
                key != SIGN_KEY_SIGN
            }
            val appKey = (parameterMap[SIGN_KEY_APP_KEY]
                ?: throw SosException(ApiResult.APP_KEY_MISSING))[0]
            val paramStr = trimedMap.toList().map { (key, valueArray) ->
                val value = if (valueArray.size == 1) {
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
        } catch (e: Exception) {
            resolver.resolveException(request, response, null, e)
        }
    }
}