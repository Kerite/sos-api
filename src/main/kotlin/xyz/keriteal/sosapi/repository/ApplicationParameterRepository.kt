package xyz.keriteal.sosapi.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import xyz.keriteal.sosapi.entity.AppParameterEntity

@Repository
interface ApplicationParameterRepository : CrudRepository<AppParameterEntity, Long> {
    fun findByAppCode(appCode: String): AppParameterEntity?
}