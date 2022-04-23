package xyz.keriteal.sosapi.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "aliyun")
@Component
class OssProperties {
    var bucketName: String = ""
    var endpoint: String = ""
    var accessKeyId: String = ""
    var accessKeySecret: String = ""
}