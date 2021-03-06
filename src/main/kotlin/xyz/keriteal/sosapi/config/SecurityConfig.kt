package xyz.keriteal.sosapi.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.entity.ContentType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import xyz.keriteal.sosapi.constants.Constants
import xyz.keriteal.sosapi.enums.ApiResult
import xyz.keriteal.sosapi.handler.JwtAuthenticationTokenFilter
import xyz.keriteal.sosapi.model.Failed

@Configuration
@EnableWebSecurity
class SecurityConfig @Autowired constructor(
    private val jwtAuthenticationTokenFilter: JwtAuthenticationTokenFilter,
    private val objectMapper: ObjectMapper
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http!!.csrf().disable()
            .exceptionHandling()
            .accessDeniedHandler { _, response, _ ->
                response.outputStream.use { out ->
                    response.contentType = ContentType.APPLICATION_JSON.mimeType
                    response.characterEncoding = "UTF-8"
                    response.status = HttpStatus.FORBIDDEN.value()
                    objectMapper.writeValue(out, Failed.fromApiResult(ApiResult.RC403))
                }
            }
            .authenticationEntryPoint { _, response, _ ->
                response.outputStream.use { out ->
                    response.contentType = ContentType.APPLICATION_JSON.mimeType
                    response.characterEncoding = "UTF-8"
                    response.status = HttpStatus.UNAUTHORIZED.value()
                    objectMapper.writeValue(out, Failed.fromApiResult(ApiResult.RC401))
                }
            }

        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.authorizeRequests()
            //??????Actuator
            .requestMatchers(EndpointRequest.toAnyEndpoint()).hasAuthority(Constants.AUTH_ACTUATOR_ADMIN)
            //??????????????????
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .antMatchers(
                "/auth/**",
                "/status/**",
                "/v3/api-docs/**",
                "/swagger-ui.html",
                "/swagger-ui/**"
            ).permitAll()
            //????????????OPTIONS
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            //?????????????????????
            .anyRequest().authenticated()
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}