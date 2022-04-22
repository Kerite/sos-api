package xyz.keriteal.sosapi.entity.relations

import org.springframework.data.jpa.domain.AbstractPersistable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user_organization")
class UserOrganization(
    @Column var userId: Long,
    @Column var orgId: Long
) : AbstractPersistable<Long>()