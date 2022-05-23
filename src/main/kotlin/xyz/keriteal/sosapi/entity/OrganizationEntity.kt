package xyz.keriteal.sosapi.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "sos_organization")
class OrganizationEntity(
    @Column(nullable = false)
    var code: String,
    @Column(nullable = false)
    var name: String,
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createTime: LocalDateTime,
    @LastModifiedDate
    @Column(nullable = false)
    var updateTime: LocalDateTime,

    @OneToMany
    @JoinColumn(
        name = "org_code",
        referencedColumnName = "code",
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    var parameters: MutableSet<OrgParameterEntity> = mutableSetOf(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
        inverseForeignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
        joinColumns = [
            JoinColumn(name = "user_id", referencedColumnName = "id")
        ],
        inverseJoinColumns = [
            JoinColumn(name = "org_id", referencedColumnName = "id")
        ]
    )
    var users: MutableSet<UserEntity> = mutableSetOf()
) : AbstractEntity()