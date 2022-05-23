package xyz.keriteal.sosapi.config

import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import xyz.keriteal.sosapi.converter.IntCodeToEnumConverterFactory
import xyz.keriteal.sosapi.converter.StringCodeToEnumConverterFactory

class WebConfig : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverterFactory(IntCodeToEnumConverterFactory())
        registry.addConverterFactory(StringCodeToEnumConverterFactory())
    }
}