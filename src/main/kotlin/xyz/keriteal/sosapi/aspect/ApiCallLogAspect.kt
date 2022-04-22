package xyz.keriteal.sosapi.aspect

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import xyz.keriteal.sosapi.annotation.ApiCallLog
import xyz.keriteal.sosapi.annotation.Logger
import xyz.keriteal.sosapi.entity.ApiLogEntity
import xyz.keriteal.sosapi.repository.ApiLogRepository
import xyz.keriteal.sosapi.utils.ipAddress

@Component
@Aspect
@Logger
class ApiCallLogAspect @Autowired constructor(
    private val logRepository: ApiLogRepository
) {

    @Pointcut("@annotation(xyz.keriteal.sosapi.annotation.ApiCallLog)")
    fun pointCut() = Unit

    @AfterReturning(returning = "result", pointcut = "pointCut() && @annotation(callLog)")
    fun logReturn(joinPoint: JoinPoint, callLog: ApiCallLog, result: Any) {
        val tag = callLog.tag
        val request = joinPoint.args[0].toString()
        val servletRequest = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes)
            .request
        val ipAddress = servletRequest.ipAddress()
        val entity = ApiLogEntity(
            tag = tag,
            requestBody = request,
            responseBody = result.toString(),
            requestIp = ipAddress,
            exception = ""
        )
        logRepository.save(entity)
    }

    @AfterThrowing(throwing = "error", pointcut = "pointCut() && @annotation(callLog)")
    fun logThrow(joinPoint: JoinPoint, callLog: ApiCallLog, error: Exception) {
        val tag = callLog.tag
        val request = joinPoint.args[0].toString()
        val servletRequest = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes)
            .request
        val ipAddress = servletRequest.ipAddress()
        val entity = ApiLogEntity(
            tag = tag,
            requestBody = request,
            exception = error.toString(),
            requestIp = ipAddress,
            responseBody = ""
        )
        logRepository.save(entity)
    }
}