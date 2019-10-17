package org.camptocamp.serac.configuration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import org.camptocamp.serac.c2c.client.C2cClient
import org.camptocamp.serac.c2c.client.C2cClientImpl
import org.camptocamp.serac.c2c.client.C2cClientRetro
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory


@Configuration
class C2cClientConfiguration {
    @Bean
    fun c2cClient(service: C2cClientRetro): C2cClient {
        return C2cClientImpl(service)
    }

    @Bean
    fun c2cClientRetro(@Value("\${c2c.url}") url: String): C2cClientRetro {
        val retrofit = Retrofit.Builder().client(OkHttpClient())
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper()))
                .build()
        return retrofit.create(C2cClientRetro::class.java)
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        val result = ObjectMapper()
        result.findAndRegisterModules()
        result.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return result
    }

}
