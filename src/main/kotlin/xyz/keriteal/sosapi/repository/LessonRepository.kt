package xyz.keriteal.sosapi.repository

import org.springframework.data.repository.CrudRepository
import xyz.keriteal.sosapi.entity.LessonEntity

interface LessonRepository : CrudRepository<LessonEntity, Long> {
}