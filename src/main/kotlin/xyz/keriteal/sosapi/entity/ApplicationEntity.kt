package xyz.keriteal.sosapi.entity

import javax.persistence.*

@Entity
@Table(name = "application")
class ApplicationEntity(
    @Column(name = "code", nullable = false) var appCode: String,
    @Column(name = "name", nullable = false) var appName: String,
    @Column(name = "key", nullable = false) var appKey: String,
    @Column(name = "secret", nullable = false) var appSecret: String,
    @Column(nullable = false) var jwtKey: String,
    @OneToMany
    @JoinColumn(name = "app_code")
    var parameters: MutableSet<AppParameterEntity> = mutableSetOf()
) : AbstractEntity()