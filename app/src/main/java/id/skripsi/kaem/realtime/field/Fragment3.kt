package id.skripsi.kaem.realtime.field

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.skripsi.kaem.realtime.databinding.Fragment3Binding

class Fragment3 : Fragment() {

    private var _binding: Fragment3Binding? = null
    private val binding get() = _binding!!

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        temp()
        pH()
        eC()
        nitro()
        fosfor()
        kalium()
    }

    private fun temp() {
        binding.apply {
            klikTemp.setOnClickListener {
                if (clGambar1.visibility == View.GONE) {
                    clGambar1.visibility = View.VISIBLE
                } else {
                    clGambar1.visibility = View.GONE
                }
            }
        }
    }

    private fun pH() {
        binding.apply {
            klikPh.setOnClickListener {
                if (clGambar2.visibility == View.GONE) {
                    clGambar2.visibility = View.VISIBLE
                } else {
                    clGambar2.visibility = View.GONE
                }
            }
        }
    }

    private fun eC() {
        binding.apply {
            klikEc.setOnClickListener {
                if (clGambar3.visibility == View.GONE) {
                    clGambar3.visibility = View.VISIBLE
                } else {
                    clGambar3.visibility = View.GONE
                }
            }
        }
    }

    private fun nitro() {
        binding.apply {
            klikNatrium.setOnClickListener {
                if (clGambar4.visibility == View.GONE) {
                    clGambar4.visibility = View.VISIBLE
                } else {
                    clGambar4.visibility = View.GONE
                }
            }
        }
    }

    private fun fosfor() {
        binding.apply {
            klikFosfor.setOnClickListener {
                if (clGambar5.visibility == View.GONE) {
                    clGambar5.visibility = View.VISIBLE
                } else {
                    clGambar5.visibility = View.GONE
                }
            }
        }
    }

    private fun kalium() {
        binding.apply {
            klikKalium.setOnClickListener {
                if (clGambar6.visibility == View.GONE) {
                    clGambar6.visibility = View.VISIBLE
                } else {
                    clGambar6.visibility = View.GONE
                }
            }
        }
    }
}