package org.camptocamp.serac.c2c.client

import org.camptocamp.serac.model.XReport
import retrofit2.Response

class C2cClientImpl(private val retro: C2cClientRetro) : C2cClient {
    override fun login(username: String, password: String): Response<LoginOutput> {
        return retro.login(Credentials(username, password)).execute()
    }

    override fun publishXReport(report: XReport, token: String): Response<CreateReportOutput> {
        return retro.publishXReport(auth(token), C2cReport.of(report)).execute()
    }

    private fun auth(token: String): String = "JWT token=\"${token}\""
}
