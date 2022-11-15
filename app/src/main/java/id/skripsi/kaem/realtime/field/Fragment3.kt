package id.skripsi.kaem.realtime.field

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkBuilder
import com.ekn.gruzer.gaugelibrary.ArcGauge
import com.ekn.gruzer.gaugelibrary.Range
import id.skripsi.kaem.realtime.MainActivity
import id.skripsi.kaem.realtime.R
import id.skripsi.kaem.realtime.databinding.Fragment3Binding
import id.skripsi.kaem.realtime.model.Status
import id.skripsi.kaem.realtime.viewmodel.SensorViewModel
import java.text.SimpleDateFormat
import java.util.*

class Fragment3 : Fragment() {

    private var _binding: Fragment3Binding? = null
    private val binding get() = _binding!!

    private val sensorViewModel: SensorViewModel by viewModels()

    private lateinit var timer: CountDownTimer
    var time_in_millis = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        timer.cancel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensorViewModel.getAllData()
        createTimer()
//        dist()
        moist()
//        temp()
//        pH()
//        eC()
//        nitro()
//        fosfor()
//        kalium()
    }

//    private fun dist() {
//        sensorViewModel.dataSuccess.observe(viewLifecycleOwner) {
//            binding.apply {
//                val dist = it.distance
//                // ketinggian air = tinggi max input sensor - tinggi input sensor
//                val tinggi = 21 - dist
//                valDist.text = "$tinggi cm"
//
//                when (tinggi) {
//                    0 -> {
//                        tvMsgDist.setText("Air Kering")
//                    }
//                    in 1..2 -> {
//                        tvMsgDist.setText("Normal")
//                    }
//                    else -> {
//                        tvMsgDist.setText("Air Pasang")
//                        showNotif()
//                    }
//                }
//            }
//            timer.start()
//        }
//
//        sensorViewModel.dataError.observe(viewLifecycleOwner) {
//            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//            timer.start()
//        }
//    }

    private fun moist() {
        sensorViewModel.getAllData().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.apply {
                        val mois = it.data!!.moisture.toDouble()
                        valRh.text = "$mois %"

                        klikRh.setOnClickListener {
                            val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_temp, null, false)

                            val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                            val tvSub = view.findViewById<TextView>(R.id.tv_sub)
                            val gauge = view.findViewById<ArcGauge>(R.id.gauge)
                            val notice = view.findViewById<TextView>(R.id.tv_notice)
                            val close = view.findViewById<Button>(R.id.btn_close)

                            tvTitle.setText("Moisture")
                            tvSub.setText("Nilai Kelembaban Tanah")
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
                            gauge.value = mois

                            notice.isGone = true

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
                    timer.start()
                }
                Status.ERROR -> {
                    it.message.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                    timer.start()
                }
                else -> {}
            }
        }
    }

//    private fun temp() {
//        sensorViewModel.dataSuccess.observe(viewLifecycleOwner) {
//            binding.apply {
//                val suhu = it.suhu.toInt().toDouble()
//                val1.text = "$suhu ℃"
//
//                klikTemp.setOnClickListener {
//                    val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_temp, null, false)
//
//                    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
//                    val tvSub = view.findViewById<TextView>(R.id.tv_sub)
//                    val gauge = view.findViewById<ArcGauge>(R.id.gauge)
//                    val notice = view.findViewById<TextView>(R.id.tv_notice)
//                    val close = view.findViewById<Button>(R.id.btn_close)
//
//                    tvTitle.setText("Temperatur")
//                    tvSub.setText("Nilai suhu lingkungan")
//                    val normal = Range()
//                    normal.color = Color.YELLOW
//                    normal.from = 0.0
//                    normal.to = 35.0
//                    gauge.addRange(normal)
//
//                    val panas = Range()
//                    panas.color = Color.RED
//                    panas.from = 36.0
//                    panas.to = 100.0
//                    gauge.addRange(panas)
//
//                    gauge.minValue = 0.0
//                    gauge.maxValue = 100.0
//                    gauge.value = suhu
//
//                    if (suhu > 35) {
//                        notice.setText("Suhu lingkungan terlalu Panas!")
//                        notice.setTextColor(Color.RED)
//                    } else {
//                        notice.setText("Suhu lingkungan Normal")
//                    }
//
//                    val builder = AlertDialog.Builder(requireContext())
//                    builder.setView(view)
//                    val dialog = builder.create()
//                    close.setOnClickListener {
//                        Toast.makeText(requireContext(), "Dialog ditutup!", Toast.LENGTH_SHORT).show()
//                        dialog.dismiss()
//                    }
//                    dialog.show()
//                }
//            }
//            timer.start()
//        }
//
//        sensorViewModel.dataError.observe(viewLifecycleOwner) {
//            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//            timer.start()
//        }
//    }

//    private fun pH() {
//        sensorViewModel.dataSuccess.observe(viewLifecycleOwner) {
//            binding.apply {
//                val pH = it.pH
//                val2.text = it.pH.toString()
//
//                klikPh.setOnClickListener {
//                    val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_temp, null, false)
//
//                    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
//                    val tvSub = view.findViewById<TextView>(R.id.tv_sub)
//                    val gauge = view.findViewById<ArcGauge>(R.id.gauge)
//                    val notice = view.findViewById<TextView>(R.id.tv_notice)
//                    val close = view.findViewById<Button>(R.id.btn_close)
//
//                    tvTitle.setText("PH")
//                    tvSub.setText("Nilai pH tanah")
//                    val asam = Range()
//                    asam.color = Color.RED
//                    asam.from = 1.0
//                    asam.to = 6.0
//                    gauge.addRange(asam)
//
//                    val normal = Range()
//                    normal.color = Color.GREEN
//                    normal.from = 7.0
//                    normal.to = 7.0
//                    gauge.addRange(normal)
//
//                    val basa = Range()
//                    basa.color = Color.parseColor("#7f00ff")
//                    basa.from = 8.0
//                    basa.to = 14.0
//                    gauge.addRange(basa)
//
//                    gauge.minValue = 1.0
//                    gauge.maxValue = 14.0
//                    gauge.value = pH.toDouble()
//
//                    if (pH < 7) {
//                        notice.setText("Kandungan pH tanah ASAM")
//                        notice.setTextColor(Color.RED)
//                    }
//                    else if (pH == 7) {
//                        notice.setText("Kandungan pH tanah Normal")
//                    } else if (pH > 7) {
//                        notice.setText("Kandungan pH tanah BASA")
//                        notice.setTextColor(Color.BLUE)
//                    }
//
//                    val builder = AlertDialog.Builder(requireContext())
//                    builder.setView(view)
//                    val dialog = builder.create()
//                    close.setOnClickListener {
//                        Toast.makeText(requireContext(), "Dialog ditutup!", Toast.LENGTH_SHORT).show()
//                        dialog.dismiss()
//                    }
//                    dialog.show()
//                }
//            }
//            timer.start()
//        }
//
//        sensorViewModel.dataError.observe(viewLifecycleOwner) {
//            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//            timer.start()
//        }
//    }

//    private fun eC() {
//        sensorViewModel.dataSuccess.observe(viewLifecycleOwner) {
//            binding.apply {
//                val ec = it.eC
//                val3.text = "$ec µS/cm"
//
//                klikEc.setOnClickListener {
//                    val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_temp, null, false)
//
//                    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
//                    val tvSub = view.findViewById<TextView>(R.id.tv_sub)
//                    val gauge = view.findViewById<ArcGauge>(R.id.gauge)
//                    val notice = view.findViewById<TextView>(R.id.tv_notice)
//                    val close = view.findViewById<Button>(R.id.btn_close)
//
//                    tvTitle.setText("EC")
//                    tvSub.setText("Nilai Ec tanah")
//                    val value = Range()
//                    value.color = ContextCompat.getColor(requireContext(), R.color.biru)
//                    value.from = 0.0
//                    value.to = 1000.0
//                    gauge.addRange(value)
//
//                    gauge.minValue = 0.0
//                    gauge.maxValue = 1000.0
//                    gauge.value = ec.toDouble()
//
//                    notice.isGone = true
//
//                    val builder = AlertDialog.Builder(requireContext())
//                    builder.setView(view)
//                    val dialog = builder.create()
//                    close.setOnClickListener {
//                        Toast.makeText(requireContext(), "Dialog ditutup!", Toast.LENGTH_SHORT).show()
//                        dialog.dismiss()
//                    }
//                    dialog.show()
//                }
//            }
//            timer.start()
//        }
//
//        sensorViewModel.dataError.observe(viewLifecycleOwner) {
//            timer.start()
//            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//        }
//    }

//    private fun nitro() {
//        sensorViewModel.dataSuccess.observe(viewLifecycleOwner) {
//            binding.apply {
//                val nitro = it.nitrogen
//                val4.text = "$nitro mg/Kg"
//
//                klikNatrium.setOnClickListener {
//                    val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_temp, null, false)
//
//                    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
//                    val tvSub = view.findViewById<TextView>(R.id.tv_sub)
//                    val gauge = view.findViewById<ArcGauge>(R.id.gauge)
//                    val notice = view.findViewById<TextView>(R.id.tv_notice)
//                    val close = view.findViewById<Button>(R.id.btn_close)
//
//                    tvTitle.setText("Nitrogen")
//                    tvSub.setText("Nilai Nitrogen dalam Tanah")
//                    val value = Range()
//                    value.color = ContextCompat.getColor(requireContext(), R.color.biru)
//                    value.from = 0.0
//                    value.to = 1000.0
//                    gauge.addRange(value)
//
//                    gauge.minValue = 0.0
//                    gauge.maxValue = 1000.0
//                    gauge.value = nitro.toDouble()
//
//                    notice.isGone = true
//
//                    val builder = AlertDialog.Builder(requireContext())
//                    builder.setView(view)
//                    val dialog = builder.create()
//                    close.setOnClickListener {
//                        Toast.makeText(requireContext(), "Dialog ditutup!", Toast.LENGTH_SHORT).show()
//                        dialog.dismiss()
//                    }
//                    dialog.show()
//                }
//            }
//            timer.start()
//        }
//
//        sensorViewModel.dataError.observe(viewLifecycleOwner) {
//            timer.start()
//            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//        }
//    }

//    private fun fosfor() {
//        sensorViewModel.dataSuccess.observe(viewLifecycleOwner) {
//            binding.apply {
//                val fosfor = it.phospor
//                val5.text = "$fosfor mg/Kg"
//
//                klikFosfor.setOnClickListener {
//                    val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_temp, null, false)
//
//                    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
//                    val tvSub = view.findViewById<TextView>(R.id.tv_sub)
//                    val gauge = view.findViewById<ArcGauge>(R.id.gauge)
//                    val notice = view.findViewById<TextView>(R.id.tv_notice)
//                    val close = view.findViewById<Button>(R.id.btn_close)
//
//                    tvTitle.setText("Fosfor")
//                    tvSub.setText("Nilai Fosfor dalam Tanah")
//                    val value = Range()
//                    value.color = ContextCompat.getColor(requireContext(), R.color.biru)
//                    value.from = 0.0
//                    value.to = 1000.0
//                    gauge.addRange(value)
//
//                    gauge.minValue = 0.0
//                    gauge.maxValue = 1000.0
//                    gauge.value = fosfor.toDouble()
//
//                    notice.isGone = true
//
//                    val builder = AlertDialog.Builder(requireContext())
//                    builder.setView(view)
//                    val dialog = builder.create()
//                    close.setOnClickListener {
//                        Toast.makeText(requireContext(), "Dialog ditutup!", Toast.LENGTH_SHORT).show()
//                        dialog.dismiss()
//                    }
//                    dialog.show()
//                }
//            }
//            timer.start()
//        }
//
//        sensorViewModel.dataError.observe(viewLifecycleOwner) {
//            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//            timer.start()
//        }
//    }

//    private fun kalium() {
//        sensorViewModel.dataSuccess.observe(viewLifecycleOwner) {
//            binding.apply {
//                val kal = it.kalium
//                val6.text = "$kal mg/Kg"
//
//                klikKalium.setOnClickListener {
//                    val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_temp, null, false)
//
//                    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
//                    val tvSub = view.findViewById<TextView>(R.id.tv_sub)
//                    val gauge = view.findViewById<ArcGauge>(R.id.gauge)
//                    val notice = view.findViewById<TextView>(R.id.tv_notice)
//                    val close = view.findViewById<Button>(R.id.btn_close)
//
//                    tvTitle.setText("Potassium")
//                    tvSub.setText("Nilai Kalium dalam Tanah")
//                    val value = Range()
//                    value.color = ContextCompat.getColor(requireContext(), R.color.biru)
//                    value.from = 0.0
//                    value.to = 1000.0
//                    gauge.addRange(value)
//
//                    gauge.minValue = 0.0
//                    gauge.maxValue = 1000.0
//                    gauge.value = kal.toDouble()
//
//                    notice.isGone = true
//
//                    val builder = AlertDialog.Builder(requireContext())
//                    builder.setView(view)
//                    val dialog = builder.create()
//                    close.setOnClickListener {
//                        Toast.makeText(requireContext(), "Dialog ditutup!", Toast.LENGTH_SHORT).show()
//                        dialog.dismiss()
//                    }
//                    dialog.show()
//                }
//            }
//            timer.start()
//        }
//
//        sensorViewModel.dataError.observe(viewLifecycleOwner) {
//            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//            timer.start()
//        }
//    }

    private fun createTimer() {
        timer = object : CountDownTimer(17000, 10) {
            override fun onTick(p0: Long) {
                time_in_millis = p0/1000
            }

            override fun onFinish() {
                sensorViewModel.getAllData()
            }
        }
    }

    private fun showNotif() {
        createNotifChannel()

        val date = Date()
        val notifId = SimpleDateFormat("ddHHmmss", Locale.US).format(date).toInt()

        val intent = Intent(requireContext(), MainActivity::class.java)
        //if you want to pass data in notif and get in required activity
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pedIntent = NavDeepLinkBuilder(requireContext())
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.fragment3)
//            .setArguments(bundleOf())
            .createPendingIntent()

        //create notif builder
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
        builder.setSmallIcon(R.mipmap.ic_ipb)
        builder.setContentTitle("Peringatan!!!")
        builder.setContentText("Air Pasang, Silahkan tutup Pintu Aktuator Field 3.")
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        //cancel notif on click
        builder.setAutoCancel(true)
        //add click intent
        builder.setContentIntent(pedIntent)

        //create notif manager
        val notifManagerCompat = NotificationManagerCompat.from(requireContext())
        notifManagerCompat.notify(notifId, builder.build())
    }

    private fun createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notif Fragment 3"
            val description = "Ini Notif Fragment 3"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationChannel.description = description

            val notifManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notifManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "channel3"
    }
}