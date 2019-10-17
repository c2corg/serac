package org.camptocamp.serac.c2c.client

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface C2cClientRetro {

    @POST("users/loginnnn")
    fun login(@Body credentials: Credentials): Call<LoginOutput>

    @POST("xreports")
    fun publishXReport(@Header("Authorization") auth: String, @Body report: C2cReport): Call<CreateReportOutput>
}
