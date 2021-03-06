package xyz.keriteal.sosapi.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import xyz.keriteal.sosapi.entity.ApiLogEntity

@Repository
interface ApiLogRepository : CrudRepository<ApiLogEntity, Long>