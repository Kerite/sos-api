package xyz.keriteal.sosapi.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.converter.ConverterFactory
import xyz.keriteal.sosapi.enums.IParameterCodeBaseEnum

class StringCodeToEnumConverterFactory : ConverterFactory<String, IParameterCodeBaseEnum> {
    companion object {
        private val CONVERTERS =
            mutableMapOf<Class<out IParameterCodeBaseEnum>, Converter<String, out IParameterCodeBaseEnum>>()
    }

    override fun <T : IParameterCodeBaseEnum> getConverter(targetType: Class<T>): Converter<String, T> {
        var converter = CONVERTERS[targetType]
        if (converter == null) {
            converter = StringToEnumConverter(targetType)
            CONVERTERS[targetType] = converter
        }
        return converter as Converter<String, T>
    }
}