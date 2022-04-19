package xyz.keriteal.sosbk.model

import javax.persistence.*

@Table(name = "user")
@Entity
open class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: String? = null

    @Column(name = "username", nullable = false)
    internal var username: String = ""

    @Column(name = "password", nullable = false)
    internal var password: String = ""
}