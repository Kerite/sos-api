package xyz.keriteal.sosapi.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.converter.ConverterFactory
import xyz.keriteal.sosapi.enums.IParameterCodeBaseEnum

class IntCodeToEnumConverterFactory : ConverterFactory<Int, IParameterCodeBaseEnum> {
    companion object {
        private val CONVERTERS =
            mutableMapOf<Class<out IParameterCodeBaseEnum>, Converter<Int, out IParameterCodeBaseEnum>>()
    }

    override fun <T : IParameterCodeBaseEnum> getConverter(targetType: Class<T>): Converter<Int, T> {
        var converter = CONVERTERS[targetType]
        if (converter == null) {
            converter = IntToEnumConverter(targetType)
            CONVERTERS[targetType] = converter
        }
        return converter as Converter<Int, T>
    }
}