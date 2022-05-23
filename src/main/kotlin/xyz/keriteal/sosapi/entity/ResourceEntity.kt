package xyz.keriteal.sosapi.entity

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "sos_resource")
class ResourceEntity(
    var resourceUrl: String,
    @Schema(description = "上传者")
    @Column
    var uploader: Int,

    @CreatedDate
    @Column
    var uploadTime: LocalDateTime,
) : AbstractEntity()