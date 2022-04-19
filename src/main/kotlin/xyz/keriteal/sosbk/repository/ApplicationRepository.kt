package xyz.keriteal.sosbk.repository

import org.springframework.data.repository.CrudRepository
import xyz.keriteal.sosbk.model.ApplicationEntity

interface ApplicationRepository : CrudRepository<ApplicationEntity, Long> {
    fun findByAppKey(appKey: String): ApplicationEntity?
}