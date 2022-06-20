package id.skripsi.kaem.realtime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.skripsi.kaem.realtime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

//    companion object {
//        private const val CHANNEL_ID = "channel1"
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        showNotif()
    }

//    private fun showNotif() {
//        createNotifChannel()
//
//        val date = Date()
//        val notifId = SimpleDateFormat("ddHHmmss", Locale.US).format(date).toInt()
//
//        val intent = Intent(this, MainActivity::class.java)
//        //if you want to pass data in notif and get in required activity
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)
//        val pedIntent = NavDeepLinkBuilder(this)
//            .setGraph(R.navigation.nav_graph)
//            .setDestination(R.id.splashFragment)
//            .setArguments(bundleOf())
//            .createPendingIntent()
//
//        //create notif builder
//        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
//        builder.setSmallIcon(R.mipmap.ic_ipb)
//        builder.setContentTitle("Peringatan!!!")
//        builder.setContentText("Air Pasang, Silahkan tutup Pintu Aktuator.")
//        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
//        builder.priority = NotificationCompat.PRIORITY_DEFAULT
//        //cancel notif on click
//        builder.setAutoCancel(true)
//        //add click intent
//        builder.setContentIntent(pedIntent)
//
//        //create notif manager
//        val notifManagerCompat = NotificationManagerCompat.from(this)
//        notifManagerCompat.notify(notifId, builder.build())
//    }

//     fun createNotifChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "My Notif"
//            val description = "My Notif channel"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
//            notificationChannel.description = description
//
//            val notifManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            notifManager.createNotificationChannel(notificationChannel)
//        }
//    }
}