package xyz.keriteal.sosapi.entity

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "sos_answer")
@Entity
@Schema(description = "答题实体类")
class AnswerEntity(
    @Column(name = "question_id", nullable = false)
    @Schema(description = "问题ID")
    var questionId: Long,
    @Column(name = "answer_content", nullable = false)
    @Schema(description = "回答内容")
    var answerContent: String,
    @Column(name = "answer_maker", nullable = false)
    @Schema(description = "作答者")
    var answerMaker: Long,
    @Column(name = "score")
    @Schema(description = "成绩")
    var score: Int,

    @CreatedDate
    @Column(name = "answer_time")
    @Schema(description = "回答时间")
    var answerTime: LocalDateTime = LocalDateTime.now()
) : AbstractEntity()