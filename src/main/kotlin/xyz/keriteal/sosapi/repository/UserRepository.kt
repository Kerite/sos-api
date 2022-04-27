package xyz.keriteal.sosapi.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import xyz.keriteal.sosapi.entity.RoleEntity
import xyz.keriteal.sosapi.entity.UserEntity
import javax.management.relation.Role

@Repository
interface UserRepository : CrudRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?

    fun existsByUsername(username: String): Boolean
}