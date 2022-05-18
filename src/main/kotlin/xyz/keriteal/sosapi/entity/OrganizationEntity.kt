package xyz.keriteal.sosapi.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "organization")
class OrganizationEntity(
    @Column(nullable = false) var code: String,
    @Column(nullable = false) var name: String,
    @Column(nullable = false) var createTime: LocalDateTime,
    @Column(nullable = false) var updateTime: LocalDateTime,
    @OneToMany
    @JoinColumn(name = "org_code")
    var parameters: MutableSet<OrgParameterEntity> = mutableSetOf()
) : AbstractEntity()