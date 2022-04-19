package xyz.keriteal.sosbk.repository

import org.springframework.data.repository.CrudRepository
import xyz.keriteal.sosbk.model.UserEntity

interface UserRepository : CrudRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
}