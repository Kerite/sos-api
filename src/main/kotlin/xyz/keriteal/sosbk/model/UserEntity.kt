package xyz.keriteal.sosbk.model

import javax.persistence.*

@Table(name = "user")
@Entity
open class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: String? = null

    @Column(name = "username", nullable = false)
    open var username: String = ""

    @Column(name = "password", nullable = false)
    open var password: String = ""
}