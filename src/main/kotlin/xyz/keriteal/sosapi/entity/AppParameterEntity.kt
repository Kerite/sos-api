package xyz.keriteal.sosapi.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "sos_app_parameter")
class AppParameterEntity(
    @Column(name = "app_code", nullable = false)
    var appCode: String,
    @Column(nullable = false)
    var key: String,
    @Column(nullable = false)
    var value: String,

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    var createTime: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    @Column(name = "update_time")
    var updateTime: LocalDateTime = LocalDateTime.now()
) : AbstractEntity()
