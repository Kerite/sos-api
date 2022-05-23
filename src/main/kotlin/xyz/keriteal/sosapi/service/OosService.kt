package xyz.keriteal.sosapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import xyz.keriteal.sosapi.config.OssConfig
import xyz.keriteal.sosapi.config.OssProperties
import xyz.keriteal.sosapi.model.SosUser
import xyz.keriteal.sosapi.repository.ResourceRepository
import java.util.*

@Service
class OosService @Autowired constructor(
    private val ossConfig: OssConfig,
    private val ossProperties: OssProperties,
    private val resourceRepository: ResourceRepository
) {
    private val ossClient = ossConfig.ossClient()

    fun upload(multipart: MultipartFile): String {
        return upload(multipart, multipart.originalFilename!!)
    }

    fun upload(multipart: MultipartFile, filename: String): String {
        multipart.inputStream.use { stream ->
            val uuid = UUID.randomUUID().toString()
                .replace("-", "")
            val authentication = SecurityContextHolder
                .getContext().authentication
            val userDetails = authentication.principal as SosUser
            // TODO 保存到数据库
            ossClient.putObject(ossProperties.bucketName, uuid, stream)
            return "https://${ossProperties.bucketName}.${ossProperties.endpoint}/$filename"
        }
    }
}