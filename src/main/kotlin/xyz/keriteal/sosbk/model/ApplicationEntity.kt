package xyz.keriteal.sosbk.model

import org.springframework.data.jpa.domain.AbstractPersistable
import javax.persistence.*

@Entity
@Table(name = "application")
class ApplicationEntity : AbstractPersistable<Long>() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "app_name", nullable = false)
    var appName: String? = null

    @Column(name = "app_key", nullable = false)
    var appKey: String? = null

    @Column(name = "app_secret", nullable = false)
    var appSecret: String? = null

    @Column(name = "app_jwt_key", nullable = false)
    var appJwtKey: String? = null
}