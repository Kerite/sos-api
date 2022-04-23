package xyz.keriteal.sosapi.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Transient

@Table(name = "user")
@Entity
class UserEntity(
    @Column(nullable = false, unique = true)
    open var username: String,
    @Column(nullable = false)
    open var password: String,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var registerTime: LocalDateTime = LocalDateTime.now(),

    @Transient var organizations: List<OrganizationEntity> = listOf()
) : AbstractEntity()