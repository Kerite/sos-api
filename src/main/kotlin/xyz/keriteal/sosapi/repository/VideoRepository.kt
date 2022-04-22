package xyz.keriteal.sosapi.repository

import org.springframework.data.repository.CrudRepository
import xyz.keriteal.sosapi.entity.VideoEntity

interface VideoRepository: CrudRepository<VideoEntity, Long> {
    fun findByUuid(uuid: String): VideoEntity?
}