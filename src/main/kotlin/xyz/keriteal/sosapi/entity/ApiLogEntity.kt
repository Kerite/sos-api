package xyz.keriteal.sosapi.entity

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "api_log")
@Entity
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