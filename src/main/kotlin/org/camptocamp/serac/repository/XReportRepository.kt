package org.camptocamp.serac.repository

import org.camptocamp.serac.model.XReport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface XReportRepository : MongoRepository<XReport, String> {
    @Query("{ '\$or': [{'custom.validated' : 'true'}, {'custom.owner' : ?0}]}")
    fun findAllValidatedOrOwned(owner: String, pageable: Pageable): Page<XReport>
}
