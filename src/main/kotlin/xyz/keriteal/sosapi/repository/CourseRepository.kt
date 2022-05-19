package xyz.keriteal.sosapi.repository

import org.springframework.data.repository.CrudRepository
import xyz.keriteal.sosapi.entity.CourseEntity

interface CourseRepository : CrudRepository<CourseEntity, Long>