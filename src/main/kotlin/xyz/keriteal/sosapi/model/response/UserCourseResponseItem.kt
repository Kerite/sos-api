package xyz.keriteal.sosapi.model.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class UserCourseResponseItem(
    @Schema(description = "课程ID")
    val courseId: Int,
    @Schema(description = "课程标题")
    val courseTitle: String,
    @Schema(description = "课程创建时间")
    val courseCreateTime: LocalDateTime,
    @Schema(description = "课程开始时间")
    val courseStartTime: LocalDateTime,
    @Schema(description = "课程结束时间")
    val courseEndTime: LocalDateTime?,
    @Schema(description = "课程的成员数量")
    val courseUsers: Int = 0,
    @Schema(description = "教师ID")
    val teacherId: Int,
    @Schema(description = "教师姓名")
    val teacherName: String,
    @Schema(description = "课程所属组织ID")
    val organizationId: Int,
    @Schema(description = "课程所属组织名称")
    val organizationName: String,
    @Schema(description = "是否开放")
    val isPublic: Boolean,
    @Schema(description = "课程标签")
    val courseTags: List<String>,
    @Schema(description = "课程封面URL")
    val cover: String
) {}