package xyz.keriteal.sosapi.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import xyz.keriteal.sosapi.entity.ApplicationEntity

@Repository
interface ApplicationRepository : CrudRepository<ApplicationEntity, Long> {
    fun findByKey(appKey: String): ApplicationEntity?
}