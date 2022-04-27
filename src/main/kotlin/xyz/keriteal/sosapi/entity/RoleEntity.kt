package xyz.keriteal.sosapi.entity

import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

@Entity
@Table(name = "role")
class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name", nullable = false, updatable = false)
    val name: String,

    @Column(nullable = false)
    val description: String,

    @ManyToMany(mappedBy = "roles")
    val users: MutableSet<UserEntity> = mutableSetOf()
) : GrantedAuthority {
    override fun getAuthority(): String {
        return name
    }
}