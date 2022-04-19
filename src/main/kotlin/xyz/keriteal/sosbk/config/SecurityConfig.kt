package xyz.keriteal.sosbk.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import xyz.keriteal.sosbk.handler.ApiSignFilter
import xyz.keriteal.sosbk.handler.JwtAuthenticationTokenFilter

@Configuration
@EnableWebSecurity
open class SecurityConfig @Autowired constructor(
    private val jwtAuthenticationTokenFilter: JwtAuthenticationTokenFilter,
    private val apiSignFilter: ApiSignFilter
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http!!.csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(

            )
            .permitAll()
            .antMatchers("/auth/**")
            .permitAll()
            .antMatchers(HttpMethod.OPTIONS)
            .permitAll()
            .anyRequest()
            .authenticated()
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilter(apiSignFilter)
    }
}