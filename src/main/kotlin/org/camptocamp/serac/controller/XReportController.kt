package org.camptocamp.serac.controller

import org.camptocamp.serac.c2c.client.C2cService
import org.camptocamp.serac.c2c.client.C2cServiceException
import org.camptocamp.serac.model.XReport
import org.camptocamp.serac.repository.XReportRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/xreports")
class XReportController(
        private val xReportRepository: XReportRepository,
        private val c2cService: C2cService
) {
    // FIXME: filter content depending on rights
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun findAll(pageable: Pageable) = xReportRepository.findAll(pageable)

    // FIXME: filter content depending on rights
    @GetMapping("/{id}")
    fun findOne(@PathVariable("id") id: String) =
            xReportRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("no matching report")

    // FIXME: filter content depending on rights
    // FIXME/ useless now?
    @GetMapping("/validated/{validated}")
    fun findAllValidated(@PathVariable("validated") validated: String, pageable: Pageable) = xReportRepository.findAllValidated(validated, pageable)

    @PostMapping
    fun create(@RequestBody report: XReport): ResponseEntity<CreateResponse> {
        report.validated = false
        // TODO: set custom data based on auth guy
        report.custom = mapOf("license_number" to "12345678", "validated" to "false")

        val createdReport: XReport = xReportRepository.save(report)
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateResponse.of(createdReport.id ?: "unknown"))
    }

    // FIXME: allow only if admin or owner and not validated
    @PutMapping("/{id}")
    fun edit(@PathVariable("id") id: String, @RequestBody report: XReport): ResponseEntity<Void> {
        // FIXME add right checks to determine if can set validated
        // FIXME if already validated, cannot be 'unvalidated', neither edited
        val previous = xReportRepository.findByIdOrNull(id) ?: return ResponseEntity.notFound().build()
        report.id = id // ensure id is set properly

        if (!previous.validated && report.validated) {
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
}
