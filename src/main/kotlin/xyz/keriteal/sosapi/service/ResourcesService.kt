package xyz.keriteal.sosapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ResourcesService @Autowired constructor(

) {
    fun getUrlFromResId(redId: Int): String {
        return "TODO"
    }

    fun saveBytes(data: Array<Byte>): Boolean {
        return true
    }
}