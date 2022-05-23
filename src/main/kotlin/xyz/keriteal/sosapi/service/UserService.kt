package xyz.keriteal.sosapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.keriteal.sosapi.enums.ApiResult
import xyz.keriteal.sosapi.exception.SosException
import xyz.keriteal.sosapi.model.SosUser
import xyz.keriteal.sosapi.model.TableResponseModel
import xyz.keriteal.sosapi.model.response.UserCourseResponseItem
import xyz.keriteal.sosapi.repository.CourseRepository
import xyz.keriteal.sosapi.repository.OrganizationRepository
import xyz.keriteal.sosapi.repository.TagRepository
import xyz.keriteal.sosapi.repository.UserRepository

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
    private val courseRepository: CourseRepository,
    private val tagRepository: TagRepository,
    private val organizationRepository: OrganizationRepository,
    private val resourcesService: ResourcesService
) : UserDetailsService {
    override fun loadUserByUsername(username: String): SosUser {
        val user = userRepository.findByUsername(username)
            ?: throw SosException(ApiResult.USERNAME_NOT_FOUND)
        return SosUser(user)
    }

    @Transactional(readOnly = true)
    fun getUserCourses(userIdOrName: String, pageSize: Int, pageIdx: Int): TableResponseModel<UserCourseResponseItem> {
        val userId = userIdOrName.toIntOrNull()
        val user = if (userId != null) {
            userRepository.findById(userId).get()
        } else {
            userRepository.findByUsername(userIdOrName)
        } ?: throw SosException(ApiResult.USER_NOT_FOUND)
        val courses = courseRepository.findByUsersId(
            user.id,
            PageRequest.of(pageIdx, pageSize)
        )
        return TableResponseModel.fromPage(courses) { courseEntity ->
            val teacherOptional = userRepository.findById(courseEntity.teacherId)
            val teacher = if (teacherOptional.isPresent) {
                teacherOptional.get()
            } else {
                throw SosException(500, "发生数据错误：找不到课程对应的教师", true)
            }
            val tags = tagRepository.findByCoursesId(courseEntity.id)
            val organizationOptional = organizationRepository.findById(courseEntity.organizationId)
            val organization = if (organizationOptional.isPresent) {
                organizationOptional.get()
            } else {
                throw SosException(500, "发生数据错误：找不到课程对应的组织", true)
            }
            return@fromPage UserCourseResponseItem(
                courseId = courseEntity.id,
                courseTitle = courseEntity.courseTitle,
                courseCreateTime = courseEntity.createTime,
                courseStartTime = courseEntity.startTime ?: courseEntity.createTime,
                courseEndTime = courseEntity.finishTime,
                courseUsers = userRepository.countByCoursesId(courseEntity.id),
                teacherId = courseEntity.teacherId,
                teacherName = teacher.username,
                organizationId = courseEntity.organizationId,
                organizationName = organization.name,
                isPublic = courseEntity.isPublic,
                courseTags = tags.map {
                    it.tagName
                },
                cover = resourcesService.getUrlFromResId(courseEntity.cover)
            )
        }
    }
}
