package com.lokalook.lokalook.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lokalook.lokalook.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    // Declare the binding variable
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment using ViewBinding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Anda dapat mengakses view dengan binding.root
        // binding.someView.text = "Hello World"

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Jangan lupa untuk set _binding ke null pada onDestroyView untuk mencegah memory leak
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString("param1", param1)
                    putString("param2", param2)
                }
            }
    }
}