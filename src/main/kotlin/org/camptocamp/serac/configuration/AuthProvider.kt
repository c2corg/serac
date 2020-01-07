package org.camptocamp.serac.configuration

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Service

@Service
class AuthProvider : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        return AuthToken(listOf(GrantedAuthority { "ROLE_ADMIN" })) // FIXME
    }

    override fun supports(authentication: Class<*>) = AuthToken::class.java.isAssignableFrom(authentication)
}
