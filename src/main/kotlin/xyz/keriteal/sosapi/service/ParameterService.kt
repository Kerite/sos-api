package xyz.keriteal.sosapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.keriteal.sosapi.repository.ApplicationParameterRepository
import xyz.keriteal.sosapi.repository.OrganizationParameterRepository

@Service
class ParameterService @Autowired constructor(
    private val applicationParameterRepository: ApplicationParameterRepository,
    private val organizationParameterRepository: OrganizationParameterRepository,
    private val applicationService: ApplicationService
) {
    fun getAppParameter(paramCode: String, defaultValue: String): String {
        val appCode = applicationService.getAppCode()
        return getAppParameter(appCode, paramCode, defaultValue)
    }

    /**
     * 获取应用的参数
     *
     * @param appCode application表的code
     * @param paramCode application表的param_code
     * @param defaultValue 参数的默认值
     */
    fun getAppParameter(appCode: String, paramCode: String, defaultValue: String): String {
        val result = applicationParameterRepository.findByAppCode(appCode)
            ?: return defaultValue
        return result.value
    }

    /**
     * 获取组织定义的参数
     */
    fun getOrgParameter(orgCode: String, paramCode: String, defaultValue: String): String {
        val result = organizationParameterRepository.findByOrgCodeAndKey(orgCode, paramCode)
            ?: return defaultValue
        return result.value
    }

    /**
     * 获取组织定义的参数列表（即一个code对应了多个值
     */
    fun getOrgParameterList(orgCode: String, paramCode: String): List<String> {
        return organizationParameterRepository.findAllByOrgCodeAndKey(orgCode, paramCode)
            .map {
                it.value
            }
    }
}