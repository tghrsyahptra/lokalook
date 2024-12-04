package com.lokalook.lokalook.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lokalook.lokalook.databinding.FragmentTrendBinding
import com.lokalook.lokalook.ui.viewmodels.MainViewModel
import com.lokalook.lokalook.ui.adapters.VerticalAdapter
import com.lokalook.lokalook.utils.Result
import com.lokalook.lokalook.ui.viewmodels.ViewModelFactory

class TrendFragment : Fragment() {
//
//    private var _binding: FragmentTrendBinding? = null
//    private val binding get() = _binding!!
//
//    private lateinit var verticalAdapter: VerticalAdapter
//
//    // Instance ViewModel
//    private val viewModel by viewModels<MainViewModel> {
//        ViewModelFactory.getInstance(requireActivity())
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        // Menginflasi layout untuk fragment ini
//        _binding = FragmentTrendBinding.inflate(inflater, container, false)
//        return binding.root
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Inisialisasi adapter untuk RecyclerView
//        verticalAdapter = VerticalAdapter { trend ->
//            // Menangani perubahan status favorit untuk tren
//            if (trend.isFavorite == true) {
//                viewModel.deleteTrend(trend) // Hapus dari favorit
//            } else {
//                viewModel.saveTrend(trend) // Tambahkan ke favorit
//            }
//        }
//
//        // Atur status loading awal untuk menampilkan efek shimmer
//        verticalAdapter.setLoadingState(true)
//
//        // Amati trending data dari ViewModel
//        viewModel.getTrendingData().observe(viewLifecycleOwner) { result ->
//            when (result) {
//                is Result.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE // Tampilkan indikator loading
//                    verticalAdapter.setLoadingState(true)
//                }
//
//                is Result.Success -> {
//                    binding.progressBar.visibility = View.GONE // Sembunyikan indikator loading
//                    verticalAdapter.setLoadingState(false)
//                    verticalAdapter.submitList(result.data) // Kirim daftar tren ke adapter
//                }
//
//                is Result.Error -> {
//                    binding.progressBar.visibility = View.GONE // Sembunyikan indikator loading saat terjadi error
//                }
//            }
//        }
//
//        // Siapkan RecyclerView
//        binding.recyclerView.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = verticalAdapter
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null // Bersihkan binding saat view dihancurkan
//    }
}