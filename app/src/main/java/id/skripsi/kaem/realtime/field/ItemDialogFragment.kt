package id.skripsi.kaem.realtime.field

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isGone
import androidx.fragment.app.DialogFragment
import com.ekn.gruzer.gaugelibrary.ArcGauge
import com.ekn.gruzer.gaugelibrary.Range
import id.skripsi.kaem.realtime.R

class ItemDialogFragment(
    var nilai: Int,
    val Title: String,
    val Sub: String,
    val notif: String
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_dialog, container, false)
    }

    override fun onResume() {
        super.onResume()
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val tvSub = view.findViewById<TextView>(R.id.tv_sub)
        val gauge = view.findViewById<ArcGauge>(R.id.gauge)
        val notice = view.findViewById<TextView>(R.id.tv_notice)
        val close = view.findViewById<Button>(R.id.btn_close)

        tvTitle.setText(Title)
        tvSub.setText(Sub)
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

        if (notif == "Temp") {
            if (nilai > 35) {
                notice.setText("Suhu lingkungan terlalu Panas!")
                notice.setTextColor(Color.RED)
            } else {
                notice.setText("Suhu lingkungan Normal")
            }
        }
        else if (notif == "pH") {
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
        }
        else {
            notice.isGone = true
        }

        close.setOnClickListener {
            dialog?.dismiss()
        }
    }
}