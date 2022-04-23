package xyz.keriteal.sosapi.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "answer")
@Entity
class AnswerEntity(
    @Column(nullable = false) var questionId: Long,
    @Column(nullable = false) var answerContent: String,
    @Column(nullable = false) var answerMaker: Long,
    @Column var score: Int,

    @CreatedDate
    @Column var answerTime: LocalDateTime = LocalDateTime.now()
) : AbstractEntity() {}