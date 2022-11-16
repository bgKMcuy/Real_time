package id.skripsi.kaem.realtime.network

import id.skripsi.kaem.realtime.model.ResponseSensor
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {
    @GET("users/{user}/devices/{device}/resources/{resource}")
    suspend fun getAllData(
        @Path("user") user: String,
        @Path("device") device: String,
        @Path("resource") resource: String,
        @Header("Authorization") Authorization: String
    ): ResponseSensor
}