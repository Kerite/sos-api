package xyz.keriteal.sosapi.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import xyz.keriteal.sosapi.entity.UserEntity

@Repository
interface UserRepository :
    JpaRepository<UserEntity, Int>, JpaSpecificationExecutor<UserEntity> {
    fun findByUsername(username: String): UserEntity?

    fun existsByUsername(username: String): Boolean

    /**
     * 使用课程ID获取成员
     */
    fun findByCoursesId(coursesId: Int, pageable: Pageable): Page<UserEntity>

    fun findByCoursesId(courses_id: Int): List<UserEntity>

    /**
     * 获取课程的成员数量
     */
    fun countByCoursesId(coursesId: Int): Int
}