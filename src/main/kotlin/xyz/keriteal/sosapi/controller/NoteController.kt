package xyz.keriteal.sosapi.controller

import io.swagger.v3.oas.annotations.Parameter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import xyz.keriteal.sosapi.dto.CreateNoteDto
import xyz.keriteal.sosapi.model.TableResponseModel
import xyz.keriteal.sosapi.model.request.CreateNoteRequest
import xyz.keriteal.sosapi.model.response.NoteListResponseItem
import xyz.keriteal.sosapi.service.AuthService
import xyz.keriteal.sosapi.service.NoteService

@RestController
class NoteController @Autowired constructor(
    private val noteService: NoteService,
    private val authService: AuthService
) {
    @GetMapping("/course/{courseId}/lesson/{lessonNum}/notes")
    fun notesFromLesson(
        @PathVariable lessonNum: Int,
        @PathVariable courseId: Int,
        @RequestParam("pageSize") pageSize: Int,
        @RequestParam("pageIndex") pageIndex: Int
    ): TableResponseModel<NoteListResponseItem> {
        return noteService.listNotes(courseId = courseId, lessonNum = lessonNum, pageSize, pageIndex)
    }

    @GetMapping("/notes/{noteId}")
    fun noteDetail(
        @Parameter(description = "笔记的ID") @PathVariable noteId: Int
    ) {

    }

    @PostMapping("/course/{courseId}/lesson/{lessonNum}/notes")
    fun createNote(
        @Parameter(description = "课程ID") @PathVariable courseId: Int,
        @Parameter(description = "课时") @PathVariable lessonNum: Int,
        @RequestBody body: CreateNoteRequest
    ): Int {
        val user = authService.getLoginUser()
        return noteService.createNote(
            CreateNoteDto(
                content = body.content,
                courseId = courseId,
                lessonNum = lessonNum,
                creator = user.user.id
            )
        )
    }
}