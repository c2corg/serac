package org.camptocamp.serac.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.access.ExceptionTranslationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
        val authFilter: AuthFilter,
        val authProvider: AuthProvider,
        val authEntryPoint: AuthEntryPoint
) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authProvider)
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password("{noop}password")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("{noop}admin")
                .roles("USER", "ADMIN")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .addFilterBefore(authFilter, ExceptionTranslationFilter::class.java)
                .authorizeRequests { authorizedRequest ->
                    authorizedRequest
                            .antMatchers("/xreports/**").hasRole("ADMIN") // FIXME
                            .anyRequest().authenticated()
                }
                .httpBasic()
                // although this is useless in RESTful API, ensure we do not redirect to any auth endpoint
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .sessionManagement()
                // never create an HTTP session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .csrf().disable()
    }
}
