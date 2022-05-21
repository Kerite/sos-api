package xyz.keriteal.sosapi.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme

@OpenAPIDefinition(
    info = Info(
        title = "SOS-API"
    ),
    security = [SecurityRequirement(name = "JWT")]
)
@SecurityScheme(
    type = SecuritySchemeType.HTTP,
    name = "JWT",
    scheme = "Bearer",
    `in` = SecuritySchemeIn.HEADER
)
class SwaggerConfig {}