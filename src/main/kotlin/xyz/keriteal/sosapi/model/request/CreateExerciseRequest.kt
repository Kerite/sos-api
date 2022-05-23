package xyz.keriteal.sosapi.model.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "创建练习集请求")
data class CreateExerciseRequest(
    val questions: List<CreateQuestionsRequest>,
    val courseId: Int?,
    val lessonNumer: Int?,
    @Schema(description = "答题时限")
    val timeLimit: Int?,
    @Schema(description = "总分")
    val maxScore: Int = 100,
    @Schema(description = "结束时间")
    val finishTime: LocalDateTime = LocalDateTime.now().plusDays(7)
)