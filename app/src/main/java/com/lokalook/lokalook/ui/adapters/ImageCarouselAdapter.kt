package com.lokalook.lokalook.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lokalook.lokalook.R

class ImageCarouselAdapter(private val mediaList: List<String>) : RecyclerView.Adapter<ImageCarouselAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        // Jika menggunakan URL, maka kita memuat gambar dari URL
        Glide.with(holder.imageView.context)
            .load(mediaList[position])  // Pastikan mediaList adalah List<String> yang berisi URL gambar
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = mediaList.size
}