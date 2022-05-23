package xyz.keriteal.sosapi.entity

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "sos_course")
class CourseEntity(
    @Column(nullable = false)
    @Schema(description = "开课教师")
    var teacherId: Int,
    @Column(nullable = false)
    @Schema(description = "课程标题")
    var courseTitle: String,
    @Column
    @Schema(description = "封面资源ID")
    var cover: Int,
    @Column(nullable = false)
    @Schema(description = "开课组织ID")
    var organizationId: Int,
    @Column(updatable = true, nullable = false)
    @Schema(description = "是否为公开课程")
    var isPublic: Boolean,

    @Column
    @Schema(description = "课程开始时间")
    var startTime: LocalDateTime?,

    @Column
    @Schema(description = "课程结束时间")
    var finishTime: LocalDateTime?,

    @CreatedDate
    @Column(updatable = false)
    @Schema(description = "审计：创建时间")
    var createTime: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    @Column
    @Schema(description = "审计：更新时间")
    var updateTime: LocalDateTime = LocalDateTime.now(),

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//        name = "user_course",
//        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
//        inverseForeignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
//        joinColumns = [
//            JoinColumn(name = "course_id", referencedColumnName = "id")
//        ],
//        inverseJoinColumns = [
//            JoinColumn(name = "user_id", referencedColumnName = "id")
//        ]
//    )
    @ManyToMany(mappedBy = "courses")
    var users: MutableSet<UserEntity> = mutableSetOf(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "sos_course_tag",
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
        inverseForeignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
        joinColumns = [
            JoinColumn(name = "course_id", referencedColumnName = "id")
        ],
        inverseJoinColumns = [
            JoinColumn(name = "tag_id", referencedColumnName = "id")
        ]
    )
    var tags: MutableSet<TagEntity> = mutableSetOf()
) : AbstractEntity()
