package xyz.keriteal.sosapi.utils

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import xyz.keriteal.sosapi.constants.UrlParamConstants
import javax.servlet.http.HttpServletRequest

object HttpUtil {
    fun getRequest(): HttpServletRequest {
        return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes)
            .request
    }
}

fun HttpServletRequest.ipAddress(): String {
    var ipAddress = getHeader("X-Forwarded-For")
        .notBlankOr {
            getHeader("Proxy-Client-IP")
        }.notBlankOr {
            getHeader("WL-Proxy-Client-IP")
        }.notBlankOr {
            getHeader("HTTP_CLIENT_IP")
        }.notBlankOr {
            getHeader("X-Real-IP")
        }
    if (ipAddress.isNotBlank()) {
        ipAddress = ipAddress.split(",")[0]
    }
    if (ipAddress.isNotBlank()) {
        ipAddress = remoteAddr
    }
    return ipAddress
}

private inline fun String.notBlankOr(fallBack: () -> String): String {
    if (isNotBlank()) {
        return this
    }
    return fallBack.invoke()
}