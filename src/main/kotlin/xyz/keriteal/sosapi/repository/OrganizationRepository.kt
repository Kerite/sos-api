package xyz.keriteal.sosapi.repository

import org.springframework.data.repository.CrudRepository
import xyz.keriteal.sosapi.entity.OrganizationEntity

interface OrganizationRepository: CrudRepository<OrganizationEntity, Int>