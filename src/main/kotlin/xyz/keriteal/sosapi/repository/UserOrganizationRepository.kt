package xyz.keriteal.sosapi.repository

import org.springframework.data.repository.CrudRepository
import xyz.keriteal.sosapi.entity.relations.UserOrganization

interface UserOrganizationRepository : CrudRepository<UserOrganization, Long> {
}