package xyz.keriteal.sosapi.entity

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "answer")
@Entity
class AnswerEntity(
    @Column(name = "question_id", nullable = false)
    var questionId: Long,
    @Column(name = "answer_content", nullable = false)
    var answerContent: String,
    @Column(name = "answer_maker", nullable = false)
    var answerMaker: Long,
    @Column(name = "score")
    var score: Int,

    @CreatedDate
    @Column(name = "answer_time")
    var answerTime: LocalDateTime = LocalDateTime.now()
) : AbstractEntity()