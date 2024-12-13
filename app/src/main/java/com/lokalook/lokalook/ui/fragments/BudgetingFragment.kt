package com.lokalook.lokalook.ui.fragments

import android.app.VoiceInteractor
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lokalook.lokalook.R
import com.lokalook.lokalook.databinding.FragmentBudgetingBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.*
import java.io.IOException

class BudgetingFragment : Fragment() {

    private lateinit var binding: FragmentBudgetingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgetingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.setOnClickListener {
            val budget = binding.budgetEditText.text.toString()
            val jumlahOrang = binding.jumlahOrangEditText.text.toString()

            if (budget.isNotEmpty() && jumlahOrang.isNotEmpty()) {
                kirimDataKeAPI(budget, jumlahOrang)
            } else {
                Toast.makeText(requireContext(), "Harap isi semua field", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun kirimDataKeAPI(budget: String, jumlahOrang: String) {
        val client = OkHttpClient()
        val url = "https://api-smartbudget.et.r.appspot.com/rekomendasi?budget=$budget&num_people=$jumlahOrang" // Ganti dengan endpoint API Anda

        val request = Request.Builder()
            .url(url)
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    withContext(Dispatchers.Main) {
                        Log.d("API Response", responseBody.toString())
                        // Buat instance ResultFragment
                        val resultFragment = ResultFragment()
                        // Kirim data ke ResultFragment
                        resultFragment.setResultData(responseBody.toString())
                        // Ganti fragment dengan ResultFragment
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, resultFragment) // Ganti dengan ID container Fragment Anda
                            .addToBackStack(null)
                            .commit()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Log.e("API Error", "Error: ${response.code}")
                        Toast.makeText(requireContext(), "Gagal mengirim data", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Log.e("API Error", "Error: ${e.message}")
                    Toast.makeText(requireContext(), "Gagal mengirim data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}