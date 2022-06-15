package id.skripsi.kaem.realtime.field

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.skripsi.kaem.realtime.databinding.Fragment1Binding
import id.skripsi.kaem.realtime.helper.viewModelsFactory
import id.skripsi.kaem.realtime.network.ApiClient
import id.skripsi.kaem.realtime.network.ApiService
import id.skripsi.kaem.realtime.viewmodel.SensorViewModel

class Fragment1 : Fragment() {
    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding!!

//    private lateinit var sensorAdapter: SensorAdapter

    private val apiService: ApiService by lazy { ApiClient.instance }
    private val sensorViewModel: SensorViewModel by viewModelsFactory { SensorViewModel(apiService) }

//    private val listContact = arrayListOf<Data>(
//        Data(judul = "Anna", sub = "1234554"),
//        Data(judul = "Brody", sub = "5432112"),
//        Data(judul = "David", sub = "1234565"),
//        Data(judul = "Jade", sub = "5432187"),
//        Data(judul = "John", sub = "1234534"),
//        Data(judul = "Pretty", sub = "5432123"),
//        Data(judul = "Sam", sub = "1234515"),
//        Data(judul = "Steve", sub = "6789021")
//    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensorViewModel.getAllSensor()
//        initRecyclerView()
//        observeData()
    }
}