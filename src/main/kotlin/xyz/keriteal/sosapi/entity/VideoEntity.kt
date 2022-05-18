package xyz.keriteal.sosapi.entity

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "video")
class VideoEntity(
    @Column(nullable = false) var uuid: String,
    @Column(name = "video_name", nullable = false)
    var videoName: String,
    @Column(name = "video_type", nullable = false)
    var videoType: Int = 0,
    @Column(name = "enabled", nullable = false)
    var enabled: Boolean = true,

    @Column(name = "uploader", nullable = false)
    var uploader: Int,
    @CreatedDate
    @Column(name = "upload_time", nullable = false, updatable = false)
    var uploadTime: LocalDateTime = LocalDateTime.now()
) : AbstractEntity()
