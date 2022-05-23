package xyz.keriteal.sosapi.repository

import com.blinkfox.fenix.jpa.QueryFenix
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import xyz.keriteal.sosapi.entity.CourseEntity

interface CourseRepository
    : JpaRepository<CourseEntity, Long>, JpaSpecificationExecutor<CourseEntity> {
    @QueryFenix
    fun queryByUserId()

    fun findByUsersId(usersId: Int): List<CourseEntity>

    fun findByUsersId(usersId: Int, pageable: Pageable): Page<CourseEntity>
}