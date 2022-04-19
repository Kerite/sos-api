package xyz.keriteal.sosbk.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import xyz.keriteal.sosbk.SosException
import xyz.keriteal.sosbk.enum.ApiResult
import xyz.keriteal.sosbk.model.SosUser
import xyz.keriteal.sosbk.repository.UserRepository

@Service
class UserDetailService @Autowired constructor(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw SosException(ApiResult.USERNAME_NOT_FOUND)
        val authorities = AuthorityUtils.createAuthorityList("USER")
        TODO("获取权限列表")
        return SosUser(user.username, user.password, authorities)
    }
}