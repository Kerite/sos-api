package xyz.keriteal.sosapi.utils

import org.apache.commons.io.IOUtils
import org.springframework.util.StreamUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

class MultiplexRequestWrapper(
    private val request: HttpServletRequest
) : HttpServletRequestWrapper(request) {
    private var _body: ByteArray = StreamUtils.copyToByteArray(request.inputStream)
    val body get() = String(_body)

    override fun getInputStream(): ServletInputStream {
        return object : ServletInputStream() {
            val bais = ByteArrayInputStream(_body)
            override fun read(): Int {
                return bais.read()
            }

            override fun isFinished(): Boolean {
                return true
            }

            override fun isReady(): Boolean {
                return false
            }

            override fun setReadListener(listener: ReadListener?) {

            }
        }
    }
}