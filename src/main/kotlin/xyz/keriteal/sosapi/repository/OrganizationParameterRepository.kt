package xyz.keriteal.sosapi.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import xyz.keriteal.sosapi.entity.OrgParameterEntity

@Repository
interface OrganizationParameterRepository : CrudRepository<OrgParameterEntity, Long> {
    fun findByOrgCode(orgCode: String): OrgParameterEntity?
}