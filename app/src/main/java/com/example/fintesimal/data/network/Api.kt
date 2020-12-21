package com.example.fintesimal.data.network

import com.example.fintesimal.data.network.response.mapResponse
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.LoggingEventListener
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.*
import java.util.concurrent.TimeUnit

const val domain = "https://fintecimal-test.herokuapp.com/api/interview/"

interface Api {

    @GET(".")
    suspend fun getData(): Response<List<mapResponse>>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): Api {
            val baseUrl = domain
            val httpInterceptor = HttpLoggingInterceptor()
            httpInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttp = OkHttpClient.Builder()
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
                .eventListenerFactory(LoggingEventListener.Factory())
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(httpInterceptor)
                .connectTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build()

            return Retrofit.Builder()
                .client(okHttp)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }
}