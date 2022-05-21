package xyz.keriteal.sosapi.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import xyz.keriteal.sosapi.entity.NoteEntity

@Repository
interface NoteRepository : CrudRepository<NoteEntity, Int>, JpaSpecificationExecutor<NoteEntity> {
    fun findByCourseIdAndLessonNumber(pageable: Pageable, courseId: Int, lessonNumber: Int): Page<NoteEntity>
}