package xyz.keriteal.sosapi.entity

import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.AbstractPersistable
import org.springframework.data.util.ProxyUtils
import javax.persistence.*

@MappedSuperclass
abstract class AbstractEntity : Persistable<Long> {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long = -1

    override fun getId(): Long {
        return id
    }

    protected fun setId(id: Long) {
        this.id = id
    }

    @Transient
    override fun isNew(): Boolean {
        return id == -1L
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (this === other) {
            return true
        }
        if (javaClass != ProxyUtils.getUserClass(other)) {
            return false
        }
        val that = other as AbstractPersistable<*>
        return if (null == getId()) false else getId() == that.id
    }

    override fun hashCode(): Int {
        var hashCode = 17
        hashCode += (id.hashCode() * 31)
        return hashCode
    }
}