package xyz.keriteal.sosapi.entity

import org.hibernate.Hibernate
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "user", indexes = [
    Index(columnList = "username")
])
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , username = $username , password = $password , locked = $locked , registerTime = $registerTime )"
    }

}