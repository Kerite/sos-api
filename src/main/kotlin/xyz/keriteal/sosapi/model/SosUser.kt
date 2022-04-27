package xyz.keriteal.sosapi.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import xyz.keriteal.sosapi.entity.UserEntity

data class SosUser(
    val user: UserEntity
) : UserDetails {
    private var expired: Boolean = false

    override fun getAuthorities(): Collection<GrantedAuthority> = user.roles

    override fun getPassword() = user.password

    override fun getUsername() = user.username

    override fun isAccountNonExpired() = !expired

    override fun isAccountNonLocked(): Boolean = !user.locked

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}