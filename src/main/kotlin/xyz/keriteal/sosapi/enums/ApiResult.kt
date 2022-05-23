package xyz.keriteal.sosapi.enums

/**
 * API 返回的结果
 *
 * @param code 接口返回的 json 中的 code 字段
 * @param message 接口返回的json中的 message 字段
 * @param rcStatus ture: 把 code 作为 http 返回码
 * @param log true: 就算不是在开发模式中也在日志中保存
 */
enum class ApiResult(
    val code: Int,
    val message: String,
    private val rcStatus: Boolean = false,
    val log: Boolean = false,
) {
    RC200(200, "响应成功", true),
    RC400(400, "参数错误", true),
    RC401(401, "请登陆", true),
    RC403(403, "权限不足, 无法查看", true),
    RC404(404, "资源不存在", true),
    RC409(409, "资源已存在", true),
    RC500(500, "未处理的错误", true, true),

    APP_KEY_MISSING(400, "缺少appKey参数"),
    SIGN_MISSING(400, "缺少sign参数"),
    PASSWORD_MISSING(400, "密码不能为空"),
    PASSWORD_INVALID(400, "密码错误"),

    USERNAME_NOT_FOUND(401, "用户不存在"),

    APPLICATION_NOT_FOUND(403, "app_key对应的application不存在"),
    SIGN_INVALID(403, "sign参数校验失败"),
    REGISTER_NOT_ALLOWED(403, "不允许注册"),

    USER_NOT_FOUND(404, "用户不存在"),

    CONFLICT_USER(409, "用户已存在"),

    JWT_SIGN_FAILED(500, "jwt签名失败", log = true),
    APPLICATION_PARAMETER_ERROR(500, "App 配置错误", log = true);

    val httpCode: Int
        get() = if (rcStatus) code else 200
}