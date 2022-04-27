package xyz.keriteal.sosapi.enum

enum class ApiResult(
    val code: Int,
    val message: String,
    val rcStatus: Boolean = false,
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

    CONFLICT_USER(409, "用户已存在"),

    JWT_SIGN_FAILED(500, "jwt签名失败", log = true);

    val httpCode: Int
        get() = if (rcStatus) code else 200
}