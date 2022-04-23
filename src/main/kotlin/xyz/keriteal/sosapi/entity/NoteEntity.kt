package xyz.keriteal.sosapi.entity

import org.hibernate.Hibernate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "note")
class NoteEntity(
    @Column(name = "course_id", nullable = false)
    var courseId: Long? = null,

    @Column(name = "lesson_name", nullable = false)
    var lessonNumber: Int? = null,

    @Column(name = "creater_id", nullable = false)
    var createrID: Long? = null,

    @Column(name = "recommend_id", nullable = false)
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