package xyz.keriteal.sosapi.entity

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "user", indexes = [
    Index(columnList = "username")
])
class UserEntity(
    @Column(nullable = false, unique = true, length = 15)
    var username: String,
    @Column(nullable = false, length = 30)
    var password: String,
    @Column(nullable = false)
    var locked: Boolean = false,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    val registerTime: LocalDateTime = LocalDateTime.now(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_organization",
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
        inverseForeignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var organizations: MutableSet<OrganizationEntity> = mutableSetOf(),

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
        inverseForeignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: MutableSet<RoleEntity> = mutableSetOf()
): AbstractEntity()
