package xyz.keriteal.sosapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.keriteal.sosapi.enums.ApiResult
import xyz.keriteal.sosapi.exception.SosException
import xyz.keriteal.sosapi.model.response.RolesResponseItem
import xyz.keriteal.sosapi.repository.RoleRepository
import xyz.keriteal.sosapi.repository.UserRepository

@Service
class RoleService @Autowired constructor(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
) {
    fun listAllRoles(userId: Int): List<RolesResponseItem> {
        val user = userRepository.findById(userId)
        if (user.isPresent) {
            val userEntity = user.get()
            return userEntity.roles.map {
                RolesResponseItem(
                    roleId = it.id,
                    roleName = it.name,
                    roleDescription = it.description
                )
            }
        }
        return emptyList()
    }

    @Transactional
    fun addRoles(userId: Int, roleIds: Set<Long>): Boolean {
        val user = userRepository.findById(userId)
        if (!user.isPresent) {
            throw SosException(ApiResult.USER_NOT_FOUND)
        }
        val userEntity = user.get()
        val roles = roleRepository.findAllById(roleIds)
        userEntity.roles.addAll(roles)
        userRepository.save(userEntity)
        return true
    }
}