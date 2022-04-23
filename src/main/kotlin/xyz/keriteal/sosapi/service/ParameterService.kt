package xyz.keriteal.sosapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.keriteal.sosapi.repository.AppParameterRepository
import xyz.keriteal.sosapi.repository.OrganizationParameterRepository

@Service
class ParameterService @Autowired constructor(
    private val appParameterRepository: AppParameterRepository,
    private val organizationParameterRepository: OrganizationParameterRepository,
    private val applicationService: ApplicationService
) {
    fun getAppParameter(paramCode: String, defaultValue: String): String {
        val appCode = applicationService.getAppCode()
        return getAppParameter(appCode, paramCode, defaultValue)
    }

    fun getAppParameter(appCode: String, paramCode: String, defaultValue: String): String {
        val result = appParameterRepository.findByAppCode(appCode)
            ?: return defaultValue
        return result.value
    }

    fun getOrgParameter(orgCode: String, defaultValue: String): String {
        val result = organizationParameterRepository.findByOrgCode(orgCode)
            ?: return defaultValue
        return result.value
    }
}