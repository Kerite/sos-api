package xyz.keriteal.sosapi.config

import com.aliyun.oss.OSS
import com.aliyun.oss.OSSClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class OssConfig(
    private val ossProperties: OssProperties
) {
    @Bean
    fun ossClient(): OSS {
        return OSSClientBuilder().build(
            ossProperties.endpoint,
            ossProperties.accessKeyId,
            ossProperties.accessKeySecret
        )
    }
}