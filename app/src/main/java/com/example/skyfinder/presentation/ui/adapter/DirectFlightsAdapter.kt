package com.example.skyfinder.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.skyfinder.R
import com.example.skyfinder.databinding.DirectFlightsItemBinding
import com.example.skyfinder.presentation.model.ticketoffer.TicketOfferModel
import java.util.Locale

class DirectFlightsAdapter :
    ListAdapter<TicketOfferModel, DirectFlightsAdapter.DirectFlightsViewHolder>(
        DirectFlightsDiffCallback
    ) {

    class DirectFlightsViewHolder(val binding: DirectFlightsItemBinding) :
        ViewHolder(binding.root) {
        fun bind(ticketOfferModel: TicketOfferModel, context: Context, position: Int) {
            binding.apply {
                imageView.setBackgroundColor(determineImageBackground(position, context))
                titleTextView.text = ticketOfferModel.title
                timeTextView.text = formatTimeRange(ticketOfferModel.timeRange)
                priceText.text = formatPrice(ticketOfferModel.price.value)
            }
        }

        private fun determineImageBackground(position: Int, context: Context): Int {
            return when (position) {
                0 -> ContextCompat.getColor(context, R.color.red)
                1 -> ContextCompat.getColor(context, R.color.blue)
                else -> ContextCompat.getColor(context, R.color.white)
            }
        }

        private fun formatPrice(price: Int): String {
            val formattedPrice = String.format(Locale.US, "%,d", price).replace(',', ' ')
            return "$formattedPrice ₽"
        }

        private fun formatTimeRange(times: List<String>): String {
            return times.joinToString(separator = " ")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectFlightsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DirectFlightsViewHolder(
            DirectFlightsItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DirectFlightsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, holder.itemView.context, position)
    }

    companion object DirectFlightsDiffCallback : DiffUtil.ItemCallback<TicketOfferModel>() {
        override fun areItemsTheSame(
            oldItem: TicketOfferModel,
            newItem: TicketOfferModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TicketOfferModel,
            newItem: TicketOfferModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}