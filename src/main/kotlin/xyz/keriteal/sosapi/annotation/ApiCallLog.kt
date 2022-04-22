package xyz.keriteal.sosapi.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiCallLog(
    val tag: String = ""
)
