package xyz.keriteal.sosapi.entity

import org.springframework.security.core.GrantedAuthority
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "sos_role")
class RoleEntity(
    @Column(name = "name", nullable = false, updatable = false)
    val name: String,

    @Column(nullable = false)
    val description: String,

    @ManyToMany(mappedBy = "roles")
    val users: MutableSet<UserEntity> = mutableSetOf()
) : GrantedAuthority, AbstractEntity() {
    override fun getAuthority(): String {
        return name
    }
}