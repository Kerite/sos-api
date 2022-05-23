package xyz.keriteal.sosapi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import xyz.keriteal.sosapi.entity.TagEntity

interface TagRepository : JpaRepository<TagEntity, Int> {
    @Query("select t from TagEntity t inner join t.courses courses where courses.id = ?1")
    fun findByCoursesId(courseId: Int): List<TagEntity>
}