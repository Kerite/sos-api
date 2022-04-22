package xyz.keriteal.sosapi.entity

import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "organization_parameter")
class OrgParameterEntity(
    @Column var orgCode: String,
    @Column var key: String,
    @Column var value: String,
    @Column var createTime: LocalDateTime = LocalDateTime.now(),
    @Column var updateTime: LocalDateTime = LocalDateTime.now()
) : AbstractPersistable<Long>()