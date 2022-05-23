package xyz.keriteal.sosapi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import xyz.keriteal.sosapi.entity.ResourceEntity

@Repository
interface ResourceRepository: JpaRepository<ResourceEntity, Int> {

}