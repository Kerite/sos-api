package xyz.keriteal.sosapi.entity

import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.AbstractPersistable
import org.springframework.data.util.ProxyUtils
import javax.persistence.*

@MappedSuperclass
abstract class AbstractEntity : Persistable<Int> {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Int = Int.MAX_VALUE

    override fun getId(): Int {
        return id
    }

    protected fun setId(id: Int) {
        this.id = id
    }

    @Transient
    override fun isNew(): Boolean {
        return id == Int.MAX_VALUE
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
        return getId() == that.id
    }

    override fun hashCode(): Int {
        var hashCode = 17
        hashCode += (id.hashCode() * 31)
        return hashCode
    }
}
