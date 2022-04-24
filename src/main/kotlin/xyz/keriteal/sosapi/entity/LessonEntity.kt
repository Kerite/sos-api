package xyz.keriteal.sosapi.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "lesson")
class LessonEntity(
    @Column(nullable = false) var courseId: Long,
    @Column var videoId: Long,
    @Column(nullable = false) var lessonTitle: String,
    @Column var lessonContentType: Int,
    @Column var lessonText: String,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createTime: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(nullable = false)
    var updateTime: LocalDateTime = LocalDateTime.now()
) : AbstractEntity()