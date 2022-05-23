package xyz.keriteal.sosapi.entity

import io.swagger.v3.oas.annotations.media.Schema
import javax.persistence.*

@Entity
@Table(name = "sos_application")
@Schema(description = "第三方或第一方应用")
class ApplicationEntity(
    @Column(name = "code", nullable = false)
    var code: String,
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "key", nullable = false)
    var key: String,
    @Column(name = "secret", nullable = false)
    var secret: String,
    @Column(nullable = false)
    var jwtKey: String,
    @OneToMany
    @JoinColumn(
        name = "app_code",
        referencedColumnName = "code",
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    var parameters: MutableSet<AppParameterEntity> = mutableSetOf()
) : AbstractEntity()