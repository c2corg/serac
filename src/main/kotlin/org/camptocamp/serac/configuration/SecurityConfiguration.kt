package org.camptocamp.serac.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
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
                .addFilterBefore()
                .authorizeRequests { authorizedRequest ->
                    authorizedRequest
                            .antMatchers("/xreports/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
                }
                .httpBasic()
    }
}
