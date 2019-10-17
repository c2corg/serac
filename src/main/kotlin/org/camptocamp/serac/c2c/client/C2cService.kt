package org.camptocamp.serac.c2c.client

import org.camptocamp.serac.model.XReport
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class C2cService(
        private val client: C2cClient,
        @Value("\${c2c.username}") private val username: String,
        @Value("\${c2c.password}") private val password: String
) {
    var cachedToken: String? = null
    var cachedExpiry: Long? = null

    private fun login() {
        val compareEpoch = Instant.now().epochSecond - 60L
        val expiry = cachedExpiry
        val token = cachedToken
        if (token != null && expiry != null && compareEpoch < expiry) {
            return
        }
        cachedToken = null
        cachedExpiry = null
        val response = client.login(username, password)
        if (!response.isSuccessful || response.body()?.token == null || response.body()?.expire == null) {
            throw C2cServiceException()
        }
        cachedToken = response.body()?.token
        cachedExpiry = response.body()?.expire
    }

    fun publishXReport(report: XReport) {
        login()
        val response = client.publishXReport(report, cachedToken!!)
        if (!response.isSuccessful || response.body()?.document_id == null) {
            throw C2cServiceException()
        }
    }
}
