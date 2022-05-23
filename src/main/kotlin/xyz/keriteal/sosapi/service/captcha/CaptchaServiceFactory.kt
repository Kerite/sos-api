package xyz.keriteal.sosapi.service.captcha

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.keriteal.sosapi.enums.ApiResult
import xyz.keriteal.sosapi.exception.SosException

@Service
class CaptchaServiceFactory @Autowired constructor(
    private val services: List<ICaptchaService>
) {
    private val serviceMap: MutableMap<String, ICaptchaService> = mutableMapOf();

    init {
        for (service in services) {
            serviceMap[service.getCaptchaType()] = service
        }
    }

    fun getService(type: String): ICaptchaService {
        if (!serviceMap.containsKey(type)) {
            throw SosException(ApiResult.APPLICATION_PARAMETER_ERROR);
        }
        return serviceMap[type]!!;
    }
}