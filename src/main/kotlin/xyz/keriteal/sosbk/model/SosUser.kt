package xyz.keriteal.sosbk.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

class SosUser(
    private val username: String,
    private val password: String,
    private val authority: Collection<GrantedAuthority>
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