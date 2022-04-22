package xyz.keriteal.sosapi.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SosUser(
    private val username: String,
    private val password: String,
    private val authority: Collection<GrantedAuthority>,
    val userId: Long
) : UserDetails {
    var expired: Boolean = false

    override fun getAuthorities(): Collection<GrantedAuthority> = authority

    override fun getPassword() = password

    override fun getUsername() = username

    override fun isAccountNonExpired() = !expired

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}