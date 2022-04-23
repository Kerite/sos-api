package xyz.keriteal.sosapi.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "profile")
@Component
class ProfileProperties {
    var needSign: Boolean = false
    var development: Boolean = false
}