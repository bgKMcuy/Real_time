package id.skripsi.kaem.realtime.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.skripsi.kaem.realtime.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        field1()
        field2()
        field3()
    }

    private fun field1() {
        binding.apply {
            tombol1.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFragment1())
            }
        }
    }

    private fun field2() {
        binding.apply {
            tombol2.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFragment2())
            }
        }
    }

    private fun field3() {
        binding.apply {
            tombol3.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFragment3())
            }
        }
    }
}