package com.lokalook.lokalook.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lokalook.lokalook.databinding.FragmentBudgetingBinding

class BudgetingFragment : Fragment() {

    // Declare the binding variable
    private var _binding: FragmentBudgetingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment using ViewBinding
        _binding = FragmentBudgetingBinding.inflate(inflater, container, false)

        // Anda dapat mengakses view dengan binding.root
        // Contoh: binding.someTextView.text = "Hello Budgeting!"

        // Tambahkan interaksi dengan elemen UI (contoh tombol)
//        binding.calculateButton.setOnClickListener {
//            // Aksi ketika tombol dihitung ditekan
//            binding.resultTextView.text = "Hasil Perhitungan"
//        }

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
            BudgetingFragment().apply {
                arguments = Bundle().apply {
                    putString("param1", param1)
                    putString("param2", param2)
                }
            }
    }
}