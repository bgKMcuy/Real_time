package id.skripsi.kaem.realtime.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.skripsi.kaem.realtime.BuildConfig
import id.skripsi.kaem.realtime.model.Resource
import id.skripsi.kaem.realtime.repo.SensorRepo
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(private val sensorRepo: SensorRepo): ViewModel() {

    fun getAllData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(sensorRepo.getAllData(
                    BuildConfig.USER,
                    BuildConfig.DEVICE,
                    BuildConfig.RESOURCE,
                    BuildConfig.BEARER))
            )
        } catch (e: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = e.message ?: "Error"
                )
            )
        }
    }
}