package xyz.keriteal.sosapi.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration

@Configuration
class ProfileConfig @Autowired constructor(
    private val context: ApplicationContext
) {
    companion object {
        const val PROFILE_DEV = "dev"
        const val PROFILE_PROD = "prod"
    }

    fun getActiveProfile(): String = context.environment.activeProfiles[0]

    fun isDev(): Boolean =
        context.environment.activeProfiles.contains(PROFILE_DEV)

    fun isProd(): Boolean =
        context.environment.activeProfiles.contains(PROFILE_PROD)
}