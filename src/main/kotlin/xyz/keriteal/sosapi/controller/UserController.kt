package xyz.keriteal.sosapi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import xyz.keriteal.sosapi.constants.Constants
import xyz.keriteal.sosapi.service.UserService

@RestController
class UserController @Autowired constructor(
    private val userService: UserService
) {
    @GetMapping("/users/{userIdOrName}")
    @Operation(summary = "获取用户详情")
    fun getUserDetail(@PathVariable userIdOrName: String) {

    }

    @PatchMapping("/users/{userIdOrName}")
    @Operation(summary = "更新用户信息")
    fun updateUserInfo(@PathVariable userIdOrName: String) {

    }

    @GetMapping("/users/{userIdOrName}/notes")
    @Operation(summary = "获取用户笔记")
    fun getUserNotes(@PathVariable userIdOrName: String) {

    }

    @GetMapping("/users/{userIdOrName}/courses")
    @Operation(summary = "获取用户加入的课程")
    fun getUserCourses(@PathVariable userIdOrName: String) {

    }

    @GetMapping(
        "/users/{userIdOrName}/courses/{courseId}/lessons/{lessonNum}/exercise",
        produces = [Constants.APPLICATION_JSON]
    )
    @Operation(summary = "获取用户的课时作业")
    fun getLessonExercises(
        @PathVariable courseId: String,
        @PathVariable lessonNum: String,
        @PathVariable userIdOrName: String
    ) {

    }

    @GetMapping("/users/{userIdOrName}/courses/{courseId}/score")
    @Operation(summary = "获取用户的课程成绩")
    fun getCourseScore(
        @PathVariable courseId: String,
        @PathVariable userIdOrName: String
    ) {

    }
}