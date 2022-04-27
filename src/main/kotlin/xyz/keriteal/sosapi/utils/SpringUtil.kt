package xyz.keriteal.sosapi.utils

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

fun <T> pageData(pageSize: Int, current: Int, func: (Pageable) -> Page<T>)
        : Page<T> {
    val pageable = PageRequest.of(current, pageSize)
    return func.invoke(pageable)
}

fun getRequest(): HttpServletRequest {
    return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes)
        .request
}