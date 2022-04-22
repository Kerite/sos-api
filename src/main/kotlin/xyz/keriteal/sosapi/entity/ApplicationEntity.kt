package xyz.keriteal.sosapi.entity

import org.springframework.data.jpa.domain.AbstractPersistable
import javax.persistence.*

@Entity
@Table(name = "application")
class ApplicationEntity(
    @Column(name = "code", nullable = false) var appCode: String,
    @Column(name = "name", nullable = false) var appName: String,
    @Column(name = "key", nullable = false) var appKey: String,
    @Column(name = "secret", nullable = false) var appSecret: String,
    @Column(nullable = false) var jwtKey: String
) : AbstractPersistable<Long>() {}