package xyz.keriteal.sosapi.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "org_parameter")
class OrgParameterEntity(
    @Column(name = "org_code") var orgCode: String,
    @Column var key: String,
    @Column var value: String,
    @Column var createTime: LocalDateTime = LocalDateTime.now(),
    @Column var updateTime: LocalDateTime = LocalDateTime.now()
) : AbstractEntity()