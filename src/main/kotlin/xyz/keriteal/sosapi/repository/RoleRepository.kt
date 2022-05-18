package xyz.keriteal.sosapi.repository

import org.springframework.data.repository.CrudRepository
import xyz.keriteal.sosapi.entity.RoleEntity

interface RoleRepository:CrudRepository<RoleEntity, Long> {

}