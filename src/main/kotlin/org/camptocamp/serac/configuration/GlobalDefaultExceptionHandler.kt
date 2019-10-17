package org.camptocamp.serac.configuration

import org.apache.logging.log4j.LogManager
import org.camptocamp.serac.c2c.client.C2cServiceException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalDefaultExceptionHandler {
    companion object {
        private val logger = LogManager.getLogger()
    }

    @ExceptionHandler(C2cServiceException::class)
    fun c2cExceptionHandler(): ResponseEntity<String> {
        return ResponseEntity("Unable to push to camptocamp API", HttpStatus.SERVICE_UNAVAILABLE)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun accessDeniedException(e: Exception): ResponseEntity<String> {
        return ResponseEntity("Access denied", HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(Exception::class)
    fun defaultExceptionHandler(e: Exception): ResponseEntity<String> {
        logger.info(e)
        return ResponseEntity("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
