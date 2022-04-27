package xyz.keriteal.sosapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import xyz.keriteal.sosapi.SosException
import xyz.keriteal.sosapi.enum.ApiResult
import xyz.keriteal.sosapi.model.SosUser
import xyz.keriteal.sosapi.repository.UserRepository

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): SosUser {
        val user = userRepository.findByUsername(username)
            ?: throw SosException(ApiResult.USERNAME_NOT_FOUND)
        return SosUser(user)
    }
}