package org.camptocamp.serac.c2c.client

import org.camptocamp.serac.model.XReport
import retrofit2.Response

interface C2cClient {
    fun login(username: String, password: String): Response<LoginOutput>

    fun publishXReport(report: XReport, token: String): Response<CreateReportOutput>
}
