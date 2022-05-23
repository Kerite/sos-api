package xyz.keriteal.sosapi.enums

enum class EnumQuestionCreateType(
    private val code: Int
) : IParameterCodeBaseEnum {
    CREATE(0), REFERENCE(1);

    override fun getCode(): Int {
        return code
    }
}