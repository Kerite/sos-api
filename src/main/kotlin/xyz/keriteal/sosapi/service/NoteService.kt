package xyz.keriteal.sosapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import xyz.keriteal.sosapi.dto.CreateNoteDto
import xyz.keriteal.sosapi.entity.NoteEntity
import xyz.keriteal.sosapi.enum.ApiResult
import xyz.keriteal.sosapi.exception.SosException
import xyz.keriteal.sosapi.model.TableResponseModel
import xyz.keriteal.sosapi.model.response.NoteDetailResponse
import xyz.keriteal.sosapi.model.response.NoteListResponseItem
import xyz.keriteal.sosapi.repository.NoteRepository
import xyz.keriteal.sosapi.repository.UserRepository

@Service
class NoteService @Autowired constructor(
    private val noteRepository: NoteRepository,
    private val userRepository: UserRepository
) {
    fun listNotes(
        courseId: Int,
        lessonNum: Int,
        pageIndex: Int,
        pageSize: Int
    ): TableResponseModel<NoteListResponseItem> {
        val page =
            noteRepository.findByCourseIdAndLessonNumber(PageRequest.of(pageIndex, pageSize), courseId, lessonNum)
        return TableResponseModel.fromPage(page) { note: NoteEntity ->
            val user = userRepository.findById(note.creatorId!!).get()
            return@fromPage NoteListResponseItem(
                noteId = note.id,
                noteCreatorId = note.creatorId!!,
                noteCreateTime = note.createTime!!,
                noteContent = note.content ?: "",
                noteCreatorName = user.username,
                isRecommend = note.recommend ?: false
            )
        }
    }

    fun noteDetail(
        noteId: Int
    ): NoteDetailResponse {
        val noteOpt = noteRepository.findById(noteId)
        if (noteOpt.isPresent) {
            throw SosException(ApiResult.RC404)
        }
        val note = noteOpt.get()
        val user = userRepository.findById(note.creatorId ?: -1)
        return NoteDetailResponse(
            noteId = noteId,
            noteCreatorId = if (user.isPresent) user.get().id else -1,
            createTime = note.createTime,
            updateTime = note.updateTime,
            noteContent = note.content ?: "",
            noteCreatorName = if (user.isPresent) user.get().username else ""
        )
    }

    /**
     * 创建笔记，并且返回新建的笔记的ID
     */
    fun createNote(dto: CreateNoteDto): Int {
        val note: NoteEntity = NoteEntity()
        val user = SecurityContextHolder.getContext()

        note.content = dto.content
        note.creatorId = dto.creator
        note.courseId = dto.courseId
        note.lessonNumber = dto.lessonNum

        val savedNote = noteRepository.save(note)
        return savedNote.id
    }
}