package xyz.keriteal.sosapi.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "app_parameter")
class AppParameterEntity(
    @Column(nullable = false) var appCode: String,
    @Column(nullable = false) var key: String,
    @Column(nullable = false) var value: String,
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createTime: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    @Column var updateTime: LocalDateTime = LocalDateTime.now()
) : AbstractEntity()