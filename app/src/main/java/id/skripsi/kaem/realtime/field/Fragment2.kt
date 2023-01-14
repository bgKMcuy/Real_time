package id.skripsi.kaem.realtime.field

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
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkBuilder
import dagger.hilt.android.AndroidEntryPoint
import id.skripsi.kaem.realtime.MainActivity
import id.skripsi.kaem.realtime.R
import id.skripsi.kaem.realtime.databinding.Fragment2Binding
import id.skripsi.kaem.realtime.model.Status
import id.skripsi.kaem.realtime.viewmodel.SensorViewModel

@AndroidEntryPoint
class Fragment2 : Fragment() {
    private var _binding: Fragment2Binding? = null
    private val binding get() = _binding!!

    private val sensorViewModel: SensorViewModel by viewModels()

    private lateinit var timer: CountDownTimer
    var time_in_millis = 0L

    var mapInt = mutableMapOf(
        "dist" to 0,
        "ec" to 0,
    )

    var mapDou = mutableMapOf(
        "mois" to 0.0,
        "pH" to 0.0,
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = Fragment2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        timer.cancel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load()
        createTimer()
        fetchData()
    }

    private fun load() {
        sensorViewModel.getAllData().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.isGone = true
                }
                Status.ERROR -> {
                    binding.progressBar.isGone = true
                }
                else -> {
                    binding.progressBar.isVisible = true
                }
            }
        }
    }

    private fun fetchData() {
        sensorViewModel.getAllData().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val akt = it.data!!.output.toInt()
                    val dist = it.data.distance.toInt()
                    val tinggi = 22 - (dist*-1)
                    val mois = it.data.moisture.toInt().toDouble()
                    val suhu = it.data.suhu.toInt().toDouble()
                    val pH = it.data.pH.toInt().toDouble()
                    val ec = it.data.eC
                    val nitro = it.data.nitrogen
                    val fosfor = it.data.phospor
                    val kal = it.data.kalium

                    aktOut(akt)
                    dist(tinggi)
                    mois(mois)
                    temp(suhu)
                    pH(pH)
                    eC(ec)
                    nitro(nitro)
                    fosfor(fosfor)
                    kalium(kal)

                    timer.start()
                }
                Status.ERROR -> {
                    it.message.let {
                        // Default nilai jika data gagal diambil dari server
//                        val akt = 0
//                        val tinggi = 4
//                        val mois = 0.0
//                        val suhu = 0.0
//                        val pH = 0.0
//                        val ec = 0
//                        val nitro = 0
//                        val fosfor = 0
//                        val kal = 0
//
//                        aktOut(akt)
//                        dist(tinggi)
//                        mois(mois)
//                        temp(suhu)
//                        pH(pH)
//                        eC(ec)
//                        nitro(nitro)
//                        fosfor(fosfor)
//                        kalium(kal)

                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                    timer.start()
                }
                else -> {}
            }
        }
    }

    private fun dist(nilai: Int) {
        val dist = view!!.findViewById<TextView>(R.id.val_dist)
        val tvMsgDist = view!!.findViewById<TextView>(R.id.tv_msg_dist)

        dist.text = "$nilai cm"

        when (nilai) {
            0 -> {
                tvMsgDist.setText("Air Kering")
            }
            in 1..2 -> {
                tvMsgDist.setText("Normal")
            }
            else -> {
                tvMsgDist.setText("Air Pasang")
                if (mapInt["dist"]!! <= 2){
                    showNotif(1,"Air Pasang! Tutup Aktuator Field 1.", "1", "Distance", "Notif Ketingian Air")
                }
            }
        }

        mapInt.put("dist", nilai)
    }

    private fun aktOut(nilai: Int) {
        val out = view!!.findViewById<TextView>(R.id.status_akt)

        when (nilai) {
            in 0..35 -> {
                out.setText("Tertutup")
            }
            in 36..75 -> {
                out.setText("Terbuka sebagian")
            }
            else -> {
                out.setText("Terbuka penuh")
            }
        }
    }

    private fun mois(nilai: Double) {
        binding.apply {
            valRh.text = "$nilai %"
            cdRh.setOnClickListener {
                val dialogFragment = ItemDialogFragment(nilai, "Moisture", "Tingkat Kelembaban Tanah", "mois")
                dialogFragment.show(childFragmentManager, "Moisture")
            }
        }

        when (nilai) {
            in 0.0..40.0 -> {
                if (mapDou["mois"]!! >= 40){
                    showNotif(2,"Tanah Kering.", "2", "Moisture", "Notif Kelembapan Tanah")
                }
            }
            in 76.0..100.0 -> {
                if (mapDou["mois"]!! <= 75){
                    showNotif(2,"Tanah Basah.", "2", "Moisture", "Notif Kelembapan Tanah")
                }
            }
            else -> {}
        }

        mapDou.put("mois", nilai)
    }

    private fun temp(nilai: Double) {
        binding.apply {
            val1.text = "$nilai ℃"
            cd1.setOnClickListener {
                val dialogFragment = ItemDialogFragment(nilai, "Temperatur", "Tingkat Suhu Lingkungan", "Temp")
                dialogFragment.show(childFragmentManager, "Temperature")
            }
        }
    }

    private fun pH(nilai: Double) {
        binding.apply {
            val2.text = "$nilai"
            cd2.setOnClickListener {
                val dialogFragment = ItemDialogFragment(nilai, "PH", "Tingkat pH Tanah", "pH")
                dialogFragment.show(childFragmentManager, "pH")
            }
        }

        when (nilai){
            in 3.0..5.4 -> {
                if (mapDou["pH"]!! >= 5.4) {
                    showNotif(3,"pH Rendah.", "3", "pH", "Notif pH Tanah")
                }
            }
            in 6.6..9.0 -> {
                if (mapDou["pH"]!! <= 6.6) {
                    showNotif(3,"pH Tinggi.", "3", "pH", "Notif pH Tanah")
                }
            }
            else -> {}
        }

        mapDou.put("pH", nilai)
    }

    private fun eC(nilai: Int) {
        binding.apply {
            val3.text = "$nilai µS/cm"
            cd3.setOnClickListener {
                val dialogFragment = ItemDialogFragment(nilai.toDouble(), "EC", "Tingkat Salinitas Tanah", "EC")
                dialogFragment.show(childFragmentManager, "EC")
            }
        }

        when (nilai) {
            in 0..3740 -> {}
            else -> {
                if (mapInt["ec"]!! <= 3740){
                    showNotif(4,"EC Tinggi.", "4", "EC", "Notif DHL Tanah")
                }
            }
        }

        mapInt.put("ec", nilai)
    }

    private fun nitro(nilai: Int) {
        binding.apply {
            val4.text = "$nilai mg/Kg"
            cd4.setOnClickListener {
                val dialogFragment = ItemDialogFragment(nilai.toDouble(), "Nitrogen", "Tingkat Nitrogen Tanah", "")
                dialogFragment.show(childFragmentManager, "Nitrogen")
            }
        }
    }

    private fun fosfor(nilai: Int) {
        binding.apply {
            val5.text = "$nilai mg/Kg"
            cd5.setOnClickListener {
                val dialogFragment = ItemDialogFragment(nilai.toDouble(), "Fosfor", "Tingkat Fosfor Tanah", "")
                dialogFragment.show(childFragmentManager, "Fosfor")
            }
        }
    }

    private fun kalium(nilai: Int) {
        binding.apply {
            val6.text = "$nilai mg/Kg"
            cd6.setOnClickListener {
                val dialogFragment = ItemDialogFragment(nilai.toDouble(), "Potassium", "Tingkat Kalium Tanah", "")
                dialogFragment.show(childFragmentManager, "Kalium")
            }
        }
    }

    private fun createTimer() {
        timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(p0: Long) {
                time_in_millis = p0/1000
            }

            override fun onFinish() {
                fetchData()
            }
        }
    }

    private fun showNotif(idNotif: Int,msg: String, CHANNEL_ID: String, CHANNEL_NAME: String, deskripsi: String) {
        createNotifChannel(CHANNEL_ID, CHANNEL_NAME, deskripsi)

//        val date = Date()
//        val notifId = SimpleDateFormat("ddHHmmss", Locale.US).format(date).toInt()
        val notifId = idNotif

        val intent = Intent(requireContext(), MainActivity::class.java)
        //if you want to pass data in notif and get in required activity
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pedIntent = NavDeepLinkBuilder(requireContext())
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.fragment1)
            .createPendingIntent()

        //create notif builder
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_ipb)
            .setContentTitle("Peringatan!")
            .setContentText(msg)
            .setShowWhen(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pedIntent)
            .build()

        //create notif manager
        val notifManager = NotificationManagerCompat.from(requireContext())
        notifManager.notify(notifId, builder)
    }

    private fun createNotifChannel(CHANNEL_ID: String, CHANNEL_NAME: String, deskripsi: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = deskripsi
                lightColor = Color.BLUE
                enableLights(true)
            }

            val manager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(notificationChannel)
        }
    }
}