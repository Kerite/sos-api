package xyz.keriteal.sosapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.keriteal.sosapi.constants.UrlParamConstants
import xyz.keriteal.sosapi.enums.ApiResult
import xyz.keriteal.sosapi.exception.SosException
import xyz.keriteal.sosapi.repository.ApplicationRepository
import xyz.keriteal.sosapi.utils.HttpUtil

@Service
class ApplicationService @Autowired constructor(
    private val applicationRepository: ApplicationRepository
) {
    /**
     * 获取当前Session的AppCode
     */
    fun getAppCode(): String {
        val request = HttpUtil.getRequest()
        val appKey = request.getParameter(UrlParamConstants.PARAM_APP_KEY)
            ?: throw SosException(ApiResult.APP_KEY_MISSING)
        val app = applicationRepository.findByKey(appKey)
            ?: throw SosException(ApiResult.APPLICATION_NOT_FOUND)
        return app.code
    }
}