package com.lokalook.lokalook.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.lokalook.lokalook.R
import com.lokalook.lokalook.databinding.FragmentResultBinding // Pastikan import yang benar


class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private var resultData: String? = null // Variabel untuk menyimpan data hasil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tampilkan data hasil pada TextView
        binding.resultTextView.text = resultData
    }

    // Fungsi untuk menerima data dari BudgetingFragment
    fun setResultData(data: String) {
        this.resultData = data
    }
}