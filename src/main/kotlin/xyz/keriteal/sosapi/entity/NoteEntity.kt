package xyz.keriteal.sosapi.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "sos_note")
class NoteEntity(
    @Column(name = "course_id", nullable = false)
    var courseId: Int? = null,

    @Column(name = "lesson_name", nullable = false)
    var lessonNumber: Int? = null,

    @Column(name = "creator_id", nullable = false)
    var creatorId: Int? = null,

    @Column(name = "is_recommend", nullable = false)
    var recommend: Boolean? = false,

    @Column(name = "content", nullable = false)
    var content: String? = null,

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    var createTime: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    var updateTime: LocalDateTime? = null
) : AbstractEntity()
