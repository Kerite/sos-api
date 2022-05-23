package xyz.keriteal.sosapi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
import xyz.keriteal.sosapi.model.request.CreateQuestionsRequest
import xyz.keriteal.sosapi.model.request.GetQuestionsRequest

@RestController
class ExerciseController {
    @GetMapping("/questions")
    @Operation(
        summary = "搜索题目",
        description = "从题库中搜索题目，用于教师创建课程作业"
    )
    fun getQuestions(
        @RequestParam request: GetQuestionsRequest
    ) {

    }

    @PostMapping("/exercises")
    @Operation(summary = "创建练习")
    fun createQuestions(
        @RequestBody request: List<CreateQuestionsRequest>
    ) {

    }
}