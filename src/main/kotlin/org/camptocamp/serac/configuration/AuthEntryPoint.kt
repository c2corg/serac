package org.camptocamp.serac.configuration

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * In a RESTful API, there should be no authentication entry point. Ensure we always return 401 status code.
 */
@Component
class AuthEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class)
    override fun commence(
            request: HttpServletRequest?,
            response: HttpServletResponse,
            authException: AuthenticationException?
    ) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
    }
}
