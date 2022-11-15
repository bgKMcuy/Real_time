package id.skripsi.kaem.realtime.repo

import id.skripsi.kaem.realtime.network.ApiService
import javax.inject.Inject

class SensorRepo @Inject constructor(private val apiService: ApiService) {
    suspend fun getAllData(
        user: String,
        device: String,
        resource: String,
        Authorization: String
    ) = apiService.getAllData(user, device, resource, Authorization)
}