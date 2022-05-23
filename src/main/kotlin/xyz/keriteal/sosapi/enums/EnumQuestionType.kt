package xyz.keriteal.sosapi.enums

enum class EnumQuestionType(
    private val code: Int
) : IParameterCodeBaseEnum {
    SUBJECTIVE(0), OBJECTIVE(0);

    override fun getCode(): Int {
        return code
    }
}