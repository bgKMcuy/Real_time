package id.skripsi.kaem.realtime.field

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ekn.gruzer.gaugelibrary.ArcGauge
import com.ekn.gruzer.gaugelibrary.Range
import dagger.hilt.android.AndroidEntryPoint
import id.skripsi.kaem.realtime.R
import id.skripsi.kaem.realtime.databinding.Fragment1Binding
import id.skripsi.kaem.realtime.model.Status
import id.skripsi.kaem.realtime.viewmodel.SensorViewModel

@AndroidEntryPoint
class Fragment1 : Fragment() {
    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding!!

    private val sensorViewModel: SensorViewModel by viewModels()

    private lateinit var timer: CountDownTimer
    var time_in_millis = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        timer.cancel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        sensorViewModel.getAllData()
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
        val mois = view!!.findViewById<TextView>(R.id.val_rh)
        val klikrh = view!!.findViewById<ConstraintLayout>(R.id.klik_rh)

        mois.text = "$nilai %"

        klikrh.setOnClickListener {
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
            gauge.value = nilai

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

    private fun temp(nilai: Double) {
        val mois = view!!.findViewById<TextView>(R.id.val_1)
        val klikTemp = view!!.findViewById<ConstraintLayout>(R.id.klik_temp)

        mois.text = "$nilai ℃"

        klikTemp.setOnClickListener {
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
            gauge.value = nilai.toDouble()

            if (nilai > 35) {
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

    private fun pH(nilai: Double) {
        val pH = view!!.findViewById<TextView>(R.id.val_2)
        val klikPh = view!!.findViewById<ConstraintLayout>(R.id.klik_ph)

        pH.text = "$nilai"

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
            asam.to = 5.4
            gauge.addRange(asam)

            val normal = Range()
            normal.color = Color.GREEN
            normal.from = 5.5
            normal.to = 7.0
            gauge.addRange(normal)

            val basa = Range()
            basa.color = Color.parseColor("#7f00ff")
            basa.from = 8.0
            basa.to = 14.0
            gauge.addRange(basa)

            gauge.minValue = 1.0
            gauge.maxValue = 14.0
            gauge.value = nilai

            if (nilai < 5.5) {
                notice.setText("Kandungan pH tanah ASAM!!")
                notice.setTextColor(Color.RED)
            }
            else if (nilai > 7) {
                notice.setText("Kandungan pH tanah BASA!!")
                notice.setTextColor(Color.RED)
            } else {
                notice.setText("Kandungan pH tanah Normal")
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

    private fun eC(nilai: Int) {
        val ec = view!!.findViewById<TextView>(R.id.val_3)
        val klikEc = view!!.findViewById<ConstraintLayout>(R.id.klik_ec)

        ec.text = "$nilai µS/cm"

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
            gauge.value = nilai.toDouble()

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

    private fun nitro(nilai: Int) {
        val nitro = view!!.findViewById<TextView>(R.id.val_4)
        val klikNatrium = view!!.findViewById<ConstraintLayout>(R.id.klik_natrium)

        nitro.text = "$nilai mg/Kg"

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
            gauge.value = nilai.toDouble()

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

    private fun fosfor(nilai: Int) {
        val fosfor = view!!.findViewById<TextView>(R.id.val_5)
        val klikFosfor = view!!.findViewById<ConstraintLayout>(R.id.klik_fosfor)

        fosfor.text = "$nilai mg/Kg"

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
            gauge.value = nilai.toDouble()

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

    private fun kalium(nilai: Int) {
        val kalium = view!!.findViewById<TextView>(R.id.val_6)
        val klikKalium = view!!.findViewById<ConstraintLayout>(R.id.klik_kalium)

        kalium.text = "$nilai mg/Kg"

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
            gauge.value = nilai.toDouble()

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
//            .setDestination(R.id.fragment1)
////            .setArguments(bundleOf())
//            .createPendingIntent()
//
//        //create notif builder
//        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
//        builder.setSmallIcon(R.mipmap.ic_ipb)
//        builder.setContentTitle("Peringatan!!!")
//        builder.setContentText("Air Pasang, Silahkan tutup Pintu Aktuator Field 1.")
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

//    private fun createNotifChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "Notif Fragment 1"
//            val description = "Ini Notif Fragment 1"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
//            notificationChannel.description = description
//
//            val notifManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notifManager.createNotificationChannel(notificationChannel)
//        }
//    }

//    companion object {
//        private const val CHANNEL_ID = "channel1"
//    }
}