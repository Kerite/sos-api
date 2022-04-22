package xyz.keriteal.sosapi.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "course")
class CourseEntity(
    @Column var teacherId: Long,
    @Column var courseName: String,

    @CreatedDate
    @Column(updatable = false)
    var createTime: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    @Column var updateTime: LocalDateTime = LocalDateTime.now()
) : AbstractPersistable<Long>() {}