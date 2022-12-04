package id.skripsi.kaem.realtime.field

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.DialogFragment
import com.ekn.gruzer.gaugelibrary.ArcGauge
import com.ekn.gruzer.gaugelibrary.Range
import id.skripsi.kaem.realtime.R

class ItemDialogFragment(
    var nilai: Double,
    val Title: String,
    val Sub: String,
    val msg: String
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
        normal.color = ContextCompat.getColor(requireContext(), R.color.bar)
        normal.from = gauge.minValue
        normal.to = gauge.maxValue
        gauge.addRange(normal)

        if (msg == "Temp") {
            gauge.minValue = -40.0
            gauge.maxValue = 80.0
            gauge.valueColor = ContextCompat.getColor(requireContext(), R.color.text_second)
            gauge.value = nilai

            if (nilai > 35) {
                notice.setText("Suhu lingkungan Panas!")
                notice.setTextColor(Color.RED)
            } else if (nilai < 10) {
                notice.setText("Suhu lingkungan Dingin!")
                notice.setTextColor(Color.RED)
            } else {
                notice.setText("Suhu lingkungan Normal")
            }
        }

        else if (msg == "mois") {
            gauge.minValue = 0.0
            gauge.maxValue = 100.0
            gauge.valueColor = ContextCompat.getColor(requireContext(), R.color.text_second)
            gauge.value = nilai

            if (nilai < 40) {
                notice.setText("Tanah Kering !")
                notice.setTextColor(Color.RED)
            }
            else if (nilai > 71) {
                notice.setText("Tanah Basah !")
                notice.setTextColor(Color.RED)
            } else {
                notice.setText("Tanah Lembab (Optimum)")
            }
        }

        else if (msg == "pH") {
            gauge.minValue = 0.0
            gauge.maxValue = 14.0
            gauge.valueColor = ContextCompat.getColor(requireContext(), R.color.text_second)
            gauge.value = nilai

            if (nilai < 5.5) {
                notice.setText("ASAM !")
                notice.setTextColor(Color.RED)
            }
            else if (nilai > 6.6) {
                notice.setText("BASA !")
                notice.setTextColor(Color.RED)
            } else {
                notice.setText("Optimum")
            }
        }

        else if (msg == "EC") {
            gauge.minValue = 0.0
            gauge.maxValue = 10000.0
            gauge.valueColor = ContextCompat.getColor(requireContext(), R.color.text_second)
            gauge.value = nilai

            if (nilai < 2000) {
                notice.setText("Rendah")
                notice.setTextColor(Color.RED)
            }
            else if (nilai > 3740) {
                notice.setText("Terlalu Tinggi !")
                notice.setTextColor(Color.RED)
            } else {
                notice.setText("Optimum")
            }
        }

        else {
            gauge.minValue = 0.0
            gauge.maxValue = 2000.0
            gauge.valueColor = ContextCompat.getColor(requireContext(), R.color.text_second)
            gauge.value = nilai

            notice.isGone = true
        }

        close.setOnClickListener {
            dialog?.dismiss()
        }
    }
}