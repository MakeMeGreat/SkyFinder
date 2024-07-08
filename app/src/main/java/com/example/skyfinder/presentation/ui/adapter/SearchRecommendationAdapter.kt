package com.example.skyfinder.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.skyfinder.R
import com.example.skyfinder.databinding.SearchRecommendationItemBinding
import com.example.skyfinder.presentation.model.SearchRecommendationItem

class SearchRecommendationAdapter :
    ListAdapter<SearchRecommendationItem, SearchRecommendationAdapter.SearchRecommendationViewHolder>(
        SearchDiffCallback
    ) {

    class SearchRecommendationViewHolder(val binding: SearchRecommendationItemBinding) :
        ViewHolder(binding.root) {
        fun bind(item: SearchRecommendationItem) {
            binding.apply {
                searchRecommendationImage.setImageResource(determineImage(item.id))
                searchRecommendationCityText.text = item.city
                searchRecommendationReasonText.text = item.reason
            }
        }

        private fun determineImage(id: Int): Int {
            return when (id) {
                1 -> R.drawable.istanbul_image
                2 -> R.drawable.sochi_image
                else -> R.drawable.phuket_image
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchRecommendationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchRecommendationViewHolder(
            SearchRecommendationItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchRecommendationViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object SearchDiffCallback : DiffUtil.ItemCallback<SearchRecommendationItem>() {
        override fun areItemsTheSame(
            oldItem: SearchRecommendationItem,
            newItem: SearchRecommendationItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchRecommendationItem,
            newItem: SearchRecommendationItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}