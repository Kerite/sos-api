package xyz.keriteal.sosapi.entity

import javax.persistence.*

@Entity
@Table(
    name = "sos_tag",
    indexes = [
        Index(columnList = "id")
    ]
)
class TagEntity(
    @Column
    val tagName: String,
    @Column
    val tagDescription: String,

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
//        inverseForeignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
//        joinColumns = [
//            JoinColumn(name = "tag_id", referencedColumnName = "id")
//        ],
//        inverseJoinColumns = [
//            JoinColumn(name = "course_id", referencedColumnName = "id")
//        ]
//    )
    @ManyToMany(mappedBy = "tags")
    var courses: MutableSet<CourseEntity> = mutableSetOf()
) : AbstractEntity() {}