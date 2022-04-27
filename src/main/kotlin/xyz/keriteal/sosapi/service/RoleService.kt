package xyz.keriteal.sosapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.keriteal.sosapi.model.response.RolesResponseItem
import xyz.keriteal.sosapi.repository.UserRepository

@Service
class RoleService @Autowired constructor(
    private val userRepository: UserRepository
) {
    fun listAllRoles(userId: Long): List<RolesResponseItem> {
        val user = userRepository.findById(userId)
        if (user.isPresent) {
            val userEntity = user.get()
            return userEntity.roles.map {
                RolesResponseItem(
                    roleId = it.id!!,
                    roleName = it.name,
                    roleDescription = it.description
                )
            }
        }
        return emptyList()
    }
}