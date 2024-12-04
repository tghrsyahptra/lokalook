package com.lokalook.lokalook.ui.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.lokalook.lokalook.R
import com.lokalook.lokalook.databinding.ItemNormalHorizontalBinding
import com.lokalook.lokalook.databinding.ItemShimmerHorizontalBinding
import com.lokalook.lokalook.ui.activities.DetailActivity

class HorizontalAdapter(
//    private var isLoading: Boolean = true,
//    private val onFavoriteClick: (WisatatEntity) -> Unit,
//) :
//    ListAdapter<EventEntity, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
//
//    override fun getItemViewType(position: Int): Int {
//        return if (isLoading) VIEW_TYPE_SHIMMER else VIEW_TYPE_DATA
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return if (viewType == VIEW_TYPE_SHIMMER) {
//            val binding = ItemShimmerHorizontalBinding.inflate(
//                LayoutInflater.from(parent.context), parent, false
//            )
//            ShimmerViewHolder(binding)
//        } else {
//            val binding = ItemNormalHorizontalBinding.inflate(
//                LayoutInflater.from(parent.context), parent, false
//            )
//            MyViewHolder(binding)
//        }
//    }

//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (holder is MyViewHolder && !isLoading) {
//            val event = getItem(position)
//            holder.bind(event)
//            val favoriteImageView = holder.binding.favoriteImageView
//            favoriteImageView.setImageResource(
//                if (event.isFavorite == true) R.drawable.ic_favorite
//                else R.drawable.ic_favorite_border
//            )
//            favoriteImageView.setOnClickListener {
//                onFavoriteClick(event)
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return if (isLoading) 5 else super.getItemCount()
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun setLoadingState(isLoading: Boolean) {
//        this.isLoading = isLoading
//        notifyDataSetChanged()
//    }
//
//    class ShimmerViewHolder(binding: ItemShimmerHorizontalBinding) :
//        RecyclerView.ViewHolder(binding.root)

//    class MyViewHolder(val binding: ItemNormalHorizontalBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(event: EventEntity) {
//            binding.titleTextView.text = event.name
//            binding.summaryTextView.text = event.summary
//            Glide.with(binding.root.context)
//                .load(event.imageLogo)
//                .transform(RoundedCorners(16))
//                .into(binding.imageView)
//            itemView.setOnClickListener {
//                val intent = Intent(binding.root.context, DetailActivity::class.java)
//                intent.putExtra("EXTRA_EVENT", event)
//                binding.root.context.startActivity(intent)
//            }
//        }
//    }

//    companion object {
//        private const val VIEW_TYPE_SHIMMER = 0
//        private const val VIEW_TYPE_DATA = 1
//
//        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
//            override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            @SuppressLint("DiffUtilEquals")
//            override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
)
//}