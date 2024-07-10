package com.example.skyfinder.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.skyfinder.R
import com.example.skyfinder.databinding.MainOfferItemBinding
import com.example.skyfinder.presentation.model.offer.OfferModel
import java.util.Locale

class MainOfferAdapter :
    ListAdapter<OfferModel, MainOfferAdapter.MainOfferViewHolder>(DiffCallback) {

    class MainOfferViewHolder(val binding: MainOfferItemBinding) : ViewHolder(binding.root) {
        fun bind(offerModel: OfferModel) {
            binding.apply {
                mainOfferItemImage.setImageResource(determineImage(offerModel.id))
                mainOfferItemName.text = offerModel.title
                mainOfferItemCity.text = offerModel.town
                mainOfferItemPriceText.text = formatPrice(offerModel.price.value)
            }
        }

        private fun determineImage(id: Int): Int {
            return when (id) {
                1 -> R.drawable.main_offer_item_image_1
                2 -> R.drawable.main_offer_item_image_2
                else -> R.drawable.main_offer_item_image_3
            }
        }

        private fun formatPrice(price: Int): String {
            val formattedPrice = String.format(Locale.US, "%,d", price).replace(',', ' ')
            return "от $formattedPrice ₽"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainOfferViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MainOfferViewHolder(
            MainOfferItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainOfferViewHolder, position: Int) {
        val offer = getItem(position)
        holder.bind(offer)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<OfferModel>() {
        override fun areItemsTheSame(oldItem: OfferModel, newItem: OfferModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: OfferModel,
            newItem: OfferModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}