package org.camptocamp.serac.configuration

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class AuthToken(authorities: Collection<GrantedAuthority>) : AbstractAuthenticationToken(authorities) {

    constructor() : this(emptyList())

    init {
        isAuthenticated = !authorities.isEmpty()
    }

    override fun getCredentials() = null

    override fun getPrincipal() = authorities.toString() // FIXME
}
