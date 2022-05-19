package xyz.keriteal.sosapi.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "test_question")
@Entity
class QuestionEntity(
    @Column(nullable = false) var courseId: Long,
    @Column(nullable = false) var lessonId: Int,
    @Column(nullable = false) var testNum: Long,
    @Column var score: Int? = null,
    @Column(nullable = false) var testType: Int,
    @CreatedDate
    @Column(nullable = false) var createTime: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    @Column(nullable = false) var updateTime: LocalDateTime = LocalDateTime.now()
) : AbstractEntity()