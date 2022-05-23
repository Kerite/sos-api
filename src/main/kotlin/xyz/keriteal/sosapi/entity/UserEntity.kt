package xyz.keriteal.sosapi.entity

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(
    name = "sos_user", indexes = [
        Index(columnList = "username")
    ]
)
class UserEntity(
    @Column(nullable = false, unique = true, length = 15)
    var username: String,
    @Column(nullable = false, length = 30)
    var password: String,
    @Column(nullable = false)
    @Schema(description = "用户是否锁定")
    var locked: Boolean = false,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    val registerTime: LocalDateTime = LocalDateTime.now(),

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//        name = "sos_user_organization",
//        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
//        inverseForeignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
//        joinColumns = [
//            JoinColumn(name = "user_id", referencedColumnName = "id")
//        ],
//        inverseJoinColumns = [
//            JoinColumn(name = "role_id", referencedColumnName = "id")
//        ]
//    )
    @ManyToMany(mappedBy = "users")
    var organizations: MutableSet<OrganizationEntity> = mutableSetOf(),

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "sos_user_role",
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
        inverseForeignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
        joinColumns = [
            JoinColumn(name = "user_id", referencedColumnName = "id")
        ],
        inverseJoinColumns = [
            JoinColumn(name = "role_id", referencedColumnName = "id")
        ]
    )
    var roles: MutableSet<RoleEntity> = mutableSetOf(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "sos_user_course",
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
        inverseForeignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
        joinColumns = [
            JoinColumn(name = "user_id", referencedColumnName = "id")
        ],
        inverseJoinColumns = [
            JoinColumn(name = "course_id", referencedColumnName = "id")
        ]
    )
    val courses: MutableSet<CourseEntity> = mutableSetOf(),
) : AbstractEntity()
