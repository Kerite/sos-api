package xyz.keriteal.sosapi.converter

import org.springframework.core.convert.converter.Converter
import xyz.keriteal.sosapi.enums.IParameterCodeBaseEnum
import xyz.keriteal.sosapi.exception.SosException

class IntToEnumConverter<T : IParameterCodeBaseEnum>(enumType: Class<T>) : Converter<Int, T> {
    private val enumValMap = mutableMapOf<Int, T>()

    init {
        val enums = enumType.enumConstants
        for (e in enums) {
            enumValMap[e.getCode()] = e
        }
    }

    override fun convert(source: Int): T {
        return enumValMap[source] ?: throw SosException(400, "参数错误, 值必须为以下之一: ${enumValMap.keys.joinToString(", ")}")
    }
}