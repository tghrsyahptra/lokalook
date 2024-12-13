package com.lokalook.lokalook.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lokalook.lokalook.databinding.ItemRecommendationBinding
import com.lokalook.lokalook.model.RecommendationResponse

class RecommendationAdapter(private var recommendations: List<RecommendationResponse>) :
    RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        holder.bind(recommendations[position])
    }

    override fun getItemCount(): Int = recommendations.size

    class RecommendationViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recommendation: RecommendationResponse) {
            Glide.with(binding.root.context)
                .load(recommendation.gambar)
                .into(binding.ivGambar)
            binding.tvDestinasi.text = recommendation.destinasi
            binding.tvTotalBiaya.text = "Rp${recommendation.total_biaya}"
        }
    }

    fun updateData(newRecommendations: List<RecommendationResponse>) {
        recommendations = newRecommendations
        notifyDataSetChanged()
    }
}