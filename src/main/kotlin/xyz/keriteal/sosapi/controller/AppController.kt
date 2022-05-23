package xyz.keriteal.sosapi.controller

import org.springframework.web.bind.annotation.*
import xyz.keriteal.sosapi.model.request.CreateAppRequest
import xyz.keriteal.sosapi.model.request.UpdateAppParameterRequest

@RestController
class AppController {
    @GetMapping("/apps/{appId}")
    fun appDetails(@PathVariable appId: Int) {

    }

    @PostMapping("/apps")
    fun createApp(@RequestBody request: CreateAppRequest) {

    }

    @GetMapping("/apps/{appId}/params")
    fun getAppParameters(@PathVariable appId: String) {

    }

    @PatchMapping("/apps/{appId}/params/{paramName}")
    fun setAppParameters(
        @PathVariable appId: String,
        @PathVariable paramName: String,
        @RequestBody request: UpdateAppParameterRequest
    ) {

    }
}