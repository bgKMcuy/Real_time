package id.skripsi.kaem.realtime.field

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ekn.gruzer.gaugelibrary.ArcGauge
import com.ekn.gruzer.gaugelibrary.Range
import id.skripsi.kaem.realtime.R
import id.skripsi.kaem.realtime.databinding.Fragment2Binding
import id.skripsi.kaem.realtime.helper.viewModelsFactory
import id.skripsi.kaem.realtime.network.ApiClient
import id.skripsi.kaem.realtime.network.ApiService
import id.skripsi.kaem.realtime.viewmodel.SensorViewModel

class Fragment2 : Fragment() {

    private var _binding: Fragment2Binding? = null
    private val binding get() = _binding!!

    private val apiService: ApiService by lazy { ApiClient.instance }
    private val sensorViewModel: SensorViewModel by viewModelsFactory { SensorViewModel(apiService) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensorViewModel.getAllSensor()
        temp()
        pH()
        eC()
        nitro()
        fosfor()
        kalium()
    }

    private fun temp() {
        sensorViewModel.dataSuccess.observe(viewLifecycleOwner) {
            binding.apply {
                var suhu = it.suhu.toInt()
                val1.text = it.suhu.toString()

                klikPh.setOnClickListener {
                    val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_temp, null, false)

                    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                    val tvSub = view.findViewById<TextView>(R.id.tv_sub)
                    val gauge = view.findViewById<ArcGauge>(R.id.gauge)
                    val notice = view.findViewById<TextView>(R.id.tv_notice)
                    val close = view.findViewById<Button>(R.id.btn_close)

                    tvTitle.setText("Temperatur")
                    tvSub.setText("Nilai suhu lingkungan")
                    val normal = Range()
                    normal.color = Color.YELLOW
                    normal.from = 0.0
                    normal.to = 35.0
                    gauge.addRange(normal)

                    val panas = Range()
                    panas.color = Color.RED
                    panas.from = 36.0
                    panas.to = 100.0
                    gauge.addRange(panas)

                    gauge.minValue = 0.0
                    gauge.maxValue = 100.0
                    gauge.value = suhu.toDouble()

                    if (suhu > 35) {
                        notice.setText("Suhu lingkungan terlalu Panas!")
                        notice.setTextColor(Color.RED)
                    } else {
                        notice.setText("Suhu lingkungan Normal")
                    }

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setView(view)
                    val dialog = builder.create()
                    close.setOnClickListener {
                        Toast.makeText(requireContext(), "Dialog ditutup!", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        }

        sensorViewModel.dataError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun pH() {
        sensorViewModel.dataSuccess.observe(viewLifecycleOwner) {
            binding.apply {
                var pH = it.pH
                val2.text = it.pH.toString()

                klikPh.setOnClickListener {
                    val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_temp, null, false)

                    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                    val tvSub = view.findViewById<TextView>(R.id.tv_sub)
                    val gauge = view.findViewById<ArcGauge>(R.id.gauge)
                    val notice = view.findViewById<TextView>(R.id.tv_notice)
                    val close = view.findViewById<Button>(R.id.btn_close)

                    tvTitle.setText("PH")
                    tvSub.setText("Nilai pH tanah")
                    val asam = Range()
                    asam.color = Color.RED
                    asam.from = 1.0
                    asam.to = 6.0
                    gauge.addRange(asam)

                    val normal = Range()
                    normal.color = Color.GREEN
                    normal.from = 7.0
                    normal.to = 7.0
                    gauge.addRange(normal)

                    val basa = Range()
                    basa.color = Color.parseColor("#7f00ff")
                    basa.from = 8.0
                    basa.to = 14.0
                    gauge.addRange(basa)

                    gauge.minValue = 1.0
                    gauge.maxValue = 14.0
                    gauge.value = pH.toDouble()

                    if (pH < 7) {
                        notice.setText("Kandungan pH tanah ASAM")
                        notice.setTextColor(Color.RED)
                    }
                    else if (pH == 7) {
                        notice.setText("Kandungan pH tanah Normal")
                    } else if (pH > 7) {
                        notice.setText("Kandungan pH tanah BASA")
                        notice.setTextColor(Color.BLUE)
                    }

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setView(view)
                    val dialog = builder.create()
                    close.setOnClickListener {
                        Toast.makeText(requireContext(), "Dialog ditutup!", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        }

        sensorViewModel.dataError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun eC() {
        sensorViewModel.dataSuccess.observe(viewLifecycleOwner) {
            binding.apply {
                var ec = it.eC
                val3.text = it.eC.toString()

                klikEc.setOnClickListener {
                    val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_temp, null, false)

                    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                    val tvSub = view.findViewById<TextView>(R.id.tv_sub)
                    val gauge = view.findViewById<ArcGauge>(R.id.gauge)
                    val notice = view.findViewById<TextView>(R.id.tv_notice)
                    val close = view.findViewById<Button>(R.id.btn_close)

                    tvTitle.setText("EC")
                    tvSub.setText("Nilai Ec tanah")
                    val value = Range()
                    value.color = ContextCompat.getColor(requireContext(), R.color.biru)
                    value.from = 0.0
                    value.to = 1000.0
                    gauge.addRange(value)

                    gauge.minValue = 0.0
                    gauge.maxValue = 1000.0
                    gauge.value = ec.toDouble()

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setView(view)
                    val dialog = builder.create()
                    close.setOnClickListener {
                        Toast.makeText(requireContext(), "Dialog ditutup!", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        }

        sensorViewModel.dataError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun nitro() {
        sensorViewModel.dataSuccess.observe(viewLifecycleOwner) {
            binding.apply {
                var nitro = it.nitrogen
                val4.text = it.nitrogen.toString()

                klikNatrium.setOnClickListener {
                    val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_temp, null, false)

                    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                    val tvSub = view.findViewById<TextView>(R.id.tv_sub)
                    val gauge = view.findViewById<ArcGauge>(R.id.gauge)
                    val notice = view.findViewById<TextView>(R.id.tv_notice)
                    val close = view.findViewById<Button>(R.id.btn_close)

                    tvTitle.setText("Nitrogen")
                    tvSub.setText("Nilai Nitrogen dalam Tanah")
                    val value = Range()
                    value.color = ContextCompat.getColor(requireContext(), R.color.biru)
                    value.from = 0.0
                    value.to = 1000.0
                    gauge.addRange(value)

                    gauge.minValue = 0.0
                    gauge.maxValue = 1000.0
                    gauge.value = nitro.toDouble()

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setView(view)
                    val dialog = builder.create()
                    close.setOnClickListener {
                        Toast.makeText(requireContext(), "Dialog ditutup!", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        }

        sensorViewModel.dataError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun fosfor() {
        sensorViewModel.dataSuccess.observe(viewLifecycleOwner) {
            binding.apply {
                var fosfor = it.phospor
                val5.text = it.phospor.toString()

                klikFosfor.setOnClickListener {
                    val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_temp, null, false)

                    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                    val tvSub = view.findViewById<TextView>(R.id.tv_sub)
                    val gauge = view.findViewById<ArcGauge>(R.id.gauge)
                    val notice = view.findViewById<TextView>(R.id.tv_notice)
                    val close = view.findViewById<Button>(R.id.btn_close)

                    tvTitle.setText("Fosfor")
                    tvSub.setText("Nilai Fosfor dalam Tanah")
                    val value = Range()
                    value.color = ContextCompat.getColor(requireContext(), R.color.biru)
                    value.from = 0.0
                    value.to = 1000.0
                    gauge.addRange(value)

                    gauge.minValue = 0.0
                    gauge.maxValue = 1000.0
                    gauge.value = fosfor.toDouble()

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setView(view)
                    val dialog = builder.create()
                    close.setOnClickListener {
                        Toast.makeText(requireContext(), "Dialog ditutup!", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        }

        sensorViewModel.dataError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun kalium() {
        sensorViewModel.dataSuccess.observe(viewLifecycleOwner) {
            binding.apply {
                var kal = it.kalium
                val6.text = it.kalium.toString()

                klikKalium.setOnClickListener {
                    val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_temp, null, false)

                    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                    val tvSub = view.findViewById<TextView>(R.id.tv_sub)
                    val gauge = view.findViewById<ArcGauge>(R.id.gauge)
                    val notice = view.findViewById<TextView>(R.id.tv_notice)
                    val close = view.findViewById<Button>(R.id.btn_close)

                    tvTitle.setText("Potassium")
                    tvSub.setText("Nilai Kalium dalam Tanah")
                    val value = Range()
                    value.color = ContextCompat.getColor(requireContext(), R.color.biru)
                    value.from = 0.0
                    value.to = 1000.0
                    gauge.addRange(value)

                    gauge.minValue = 0.0
                    gauge.maxValue = 1000.0
                    gauge.value = kal.toDouble()

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setView(view)
                    val dialog = builder.create()
                    close.setOnClickListener {
                        Toast.makeText(requireContext(), "Dialog ditutup!", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        }

        sensorViewModel.dataError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}