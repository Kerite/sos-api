package xyz.keriteal.sosapi.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import xyz.keriteal.sosapi.entity.AppParameterEntity

@Repository
interface ApplicationParameterRepository : JpaRepository<AppParameterEntity, Long> {
    fun findByAppCodeAndKey(appCode: String, key: String): AppParameterEntity?

    fun existsByAppCodeAndKey(appCode: String, key: String): Boolean

    fun findAllByAppCode(appCode: String): List<AppParameterEntity>

    fun findAllByAppCode(appCode: String, pageable: Pageable): Page<AppParameterEntity>
}