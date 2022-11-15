package id.skripsi.kaem.realtime.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.skripsi.kaem.realtime.BuildConfig
import id.skripsi.kaem.realtime.model.Resource
import id.skripsi.kaem.realtime.repo.SensorRepo
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

//class SensorViewModel(private val apiService: ApiService): ViewModel() {
//
//    private val _dataSuccess = MutableLiveData<ResponData>()
//    val dataSuccess : LiveData<ResponData> get() = _dataSuccess
//
//    private val _dataError = MutableLiveData<String>()
//    val dataError : LiveData<String> get() = _dataError
//
//    fun getAllSensor() {
//        apiService.getAllData(user = BuildConfig.USER, device = BuildConfig.DEVICE, resource = BuildConfig.RESOURCE, Authorization = BuildConfig.BEARER)
//            .enqueue(object : Callback<ResponData> {
//                override fun onResponse(call: Call<ResponData>, response: Response<ResponData>) {
//                    if (response.isSuccessful) {
//                        if (response.body() != null) {
//                            _dataSuccess.postValue(response.body())
//                        } else {
//                            _dataError.postValue("Datanya kosong")
//                        }
//                    } else {
//                        _dataError.postValue("Pengambilan data gagal")
//                    }
//                }
//
//                override fun onFailure(call: Call<ResponData>, t: Throwable) {
//                    _dataError.postValue("Server Error!")
//                }
//            })
//    }
//}

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