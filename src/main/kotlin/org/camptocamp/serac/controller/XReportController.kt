package org.camptocamp.serac.controller

import org.camptocamp.serac.c2c.client.C2cService
import org.camptocamp.serac.c2c.client.C2cServiceException
import org.camptocamp.serac.model.XReport
import org.camptocamp.serac.repository.XReportRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/xreports")
class XReportController(
        private val xReportRepository: XReportRepository,
        private val c2cService: C2cService
) {
    @GetMapping
    fun findAll(pageable: Pageable, authentication: Authentication): Page<XReport> {
        return (
                if (isAdmin(authentication)) xReportRepository.findAll(pageable)
                else xReportRepository.findAllValidatedOrOwned(userId(authentication), pageable)
                )
                // always filter personal data when retrieving list
                .map { report -> XReport.public(report) }
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable("id") id: String, authentication: Authentication): XReport {
        val report = xReportRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("no matching report")
        if (isAdmin(authentication) || isOwner(authentication, report.custom["id"])) {
            // can be used for both viewing or editing
            return report
        }
        if (isValidated(report)) {
            // only view, without personal user data
            return XReport.public(report)
        }
        throw IllegalArgumentException("no matching report")
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun create(@RequestBody report: XReport): ResponseEntity<CreateResponse> {
        // TODO: set custom data based on auth guy
        report.custom = mapOf("id" to "1234", "validated" to "false")

        val createdReport: XReport = xReportRepository.save(report)
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateResponse.of(createdReport.id ?: "unknown"))
    }

    @PutMapping("/{id}")
    fun edit(
            @PathVariable("id") id: String,
            @RequestBody report: XReport,
            authentication: Authentication
    ): ResponseEntity<Void> {
        val previous = xReportRepository.findByIdOrNull(id) ?: return ResponseEntity.notFound().build()

        // check rights to edit
        if (!isAdmin(authentication) || !isOwner(authentication, id)) {
            return ResponseEntity.notFound().build()
        }
        if (isValidated(previous)) {
            // report is already validated, cannot be edited anymore
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        report.id = id // ensure id is set properly in edited report

        if (isValidated(report)) {
            if (!isAdmin(authentication)) { // only admin can validate report
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
            }
            try {
                c2cService.publishXReport(report)
            } catch (exception: C2cServiceException) {
                throw ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Unable to push to camptocamp API")
            }
        }
        xReportRepository.save(report)

        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@PathVariable("id") id: String): ResponseEntity<Void> {
        val report = xReportRepository.findByIdOrNull(id) ?: return ResponseEntity.notFound().build()
        xReportRepository.delete(report)
        return ResponseEntity.noContent().build()
    }

    fun isAdmin(authentication: Authentication) = authentication.authorities
            .stream()
            .anyMatch { r -> r.authority == "ROLE_ADMIN" }

    fun isOwner(authentication: Authentication, id: String?): Boolean {
        authentication.principal
        return "1234" == id // FIXME authentication.xxx
    }

    fun userId(authentication: Authentication): String {
        return "1234" // FIXME authentication.xxx
    }

    fun isValidated(report: XReport): Boolean {
        return report.custom["validated"] == "true"
    }
}
