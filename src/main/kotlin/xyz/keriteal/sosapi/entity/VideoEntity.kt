package xyz.keriteal.sosapi.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "video")
class VideoEntity(
    @Column(nullable = false) var uuid: String,
    @Column(nullable = false) var videoName: String,
    @Column(nullable = false) var videoType: Int = 0,
    @Column(nullable = false) var enabled: Boolean = true,

    @Column(nullable = false) var uploader: Long,
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var uploadTime: LocalDateTime = LocalDateTime.now()
) : AbstractEntity()