package xyz.keriteal.sosapi.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "api_log")
@Entity
class ApiLogEntity(
    @Column var tag: String,
    @Column var requestBody: String,
    @Column var exception: String,
    @Column var responseBody: String,
    @Column var requestIp: String,
    @CreatedDate
    @Column(updatable = false) var createTime: LocalDateTime = LocalDateTime.now()
) : AbstractEntity()