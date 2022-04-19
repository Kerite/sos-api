package xyz.keriteal.sosbk.handler

import org.apache.commons.codec.digest.Md5Crypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import xyz.keriteal.sosbk.SosException
import xyz.keriteal.sosbk.enum.ApiResult
import xyz.keriteal.sosbk.repository.ApplicationRepository
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
class ApiSignFilter @Autowired constructor(
    private val applicationRepository: ApplicationRepository
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
    }
}