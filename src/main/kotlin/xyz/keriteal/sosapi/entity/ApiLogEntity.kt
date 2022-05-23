package xyz.keriteal.sosapi.entity

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "sos_api_log")
@Entity
@Schema(description = "接口调用日志")
class ApiLogEntity(
    @Column(length = 50) var tag: String,
    @Column(nullable = true) var requestBody: String,
    @Column(nullable = true) var exception: String?,
    @Column(nullable = true) var responseBody: String?,
    @Column(nullable = false) var requestIp: String,

    @Basic(fetch = FetchType.LAZY)
    @CreatedDate
    @Column(updatable = false)
    var createTime: LocalDateTime = LocalDateTime.now()
) : AbstractEntity()