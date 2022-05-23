package xyz.keriteal.sosapi.converter

import org.springframework.core.convert.converter.Converter
import xyz.keriteal.sosapi.enums.IParameterCodeBaseEnum
import xyz.keriteal.sosapi.exception.SosException

class StringToEnumConverter<T : IParameterCodeBaseEnum>(enumType: Class<T>) : Converter<String, T> {
    private val enumValMap = mutableMapOf<String, T>()

    init {
        val enums = enumType.enumConstants
        for (e in enums) {
            enumValMap[e.getCode().toString()] = e
        }
    }

    override fun convert(source: String): T {
        return enumValMap[source] ?: throw SosException(400, "参数的值必须为以下之一: [${enumValMap.keys.joinToString(", ")}]")
    }
}