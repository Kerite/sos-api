package xyz.keriteal.sosapi.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Validated
class RequestModel {
    var sign: String = ""
    var timestamp: Long = 0
}