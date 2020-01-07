package org.camptocamp.serac.configuration

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthFilter : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        // FIXME
        if (request.getHeader("Toto") != null) {
            SecurityContextHolder.getContext().authentication = AuthToken()
        }
        filterChain.doFilter(request, response)
    }

}
