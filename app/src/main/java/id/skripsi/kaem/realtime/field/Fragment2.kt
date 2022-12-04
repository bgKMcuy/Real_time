package id.skripsi.kaem.realtime.field

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
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
                    val tinggi = 17 - (dist*-1)
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
                        //Default nilai jika data gagal diambil dari server
                        val akt = 0
                        val tinggi = 0
                        val mois = 0.0
                        val suhu = 0.0
                        val pH = 0.0
                        val ec = 0
                        val nitro = 0
                        val fosfor = 0
                        val kal = 0

                        aktOut(akt)
                        dist(tinggi)
                        mois(mois)
                        temp(suhu)
                        pH(pH)
                        eC(ec)
                        nitro(nitro)
                        fosfor(fosfor)
                        kalium(kal)

                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                    timer.cancel()
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
//                showNotif()
            }
        }
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
//                showNotif()
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
    }

    private fun eC(nilai: Int) {
        binding.apply {
            val3.text = "$nilai µS/cm"
            cd3.setOnClickListener {
                val dialogFragment = ItemDialogFragment(nilai.toDouble(), "EC", "Tingkat Salinitas Tanah", "EC")
                dialogFragment.show(childFragmentManager, "EC")
            }
        }
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

//    private fun showNotif() {
//        createNotifChannel()
//
//        val date = Date()
//        val notifId = SimpleDateFormat("ddHHmmss", Locale.US).format(date).toInt()
//
//        val intent = Intent(requireContext(), MainActivity::class.java)
//        //if you want to pass data in notif and get in required activity
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val pedIntent = NavDeepLinkBuilder(requireContext())
//            .setGraph(R.navigation.nav_graph)
//            .setDestination(R.id.fragment2)
////            .setArguments(bundleOf())
//            .createPendingIntent()
//
//        //create notif builder
//        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
//        builder.setSmallIcon(R.mipmap.ic_ipb)
//        builder.setContentTitle("Peringatan!!!")
//        builder.setContentText("Air Pasang, Silahkan tutup Pintu Aktuator Field 2.")
//        builder.priority = NotificationCompat.PRIORITY_DEFAULT
//        //cancel notif on click
//        builder.setAutoCancel(true)
//        //add click intent
//        builder.setContentIntent(pedIntent)
//
//        //create notif manager
//        val notifManagerCompat = NotificationManagerCompat.from(requireContext())
//        notifManagerCompat.notify(notifId, builder.build())
//    }
//
//    private fun createNotifChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "Notif Fragment 2"
//            val description = "Ini Notif Fragment 2"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
//            notificationChannel.description = description
//
//            val notifManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notifManager.createNotificationChannel(notificationChannel)
//        }
//    }
//
//    companion object {
//        private const val CHANNEL_ID = "channel2"
//    }
}