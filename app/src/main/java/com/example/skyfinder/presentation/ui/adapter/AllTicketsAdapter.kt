package com.example.skyfinder.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.skyfinder.R
import com.example.skyfinder.databinding.AllTicketsItemBinding
import com.example.skyfinder.presentation.model.ticket.TicketModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class AllTicketsAdapter :
    ListAdapter<TicketModel, AllTicketsAdapter.AllTicketsViewHolder>(
        AllTicketsDiffCallback
    ) {
    class AllTicketsViewHolder(val binding: AllTicketsItemBinding) : ViewHolder(binding.root) {
        fun bind(model: TicketModel, context: Context) {
            binding.apply {
                priceText.text = formatPrice(model.price.value)
                imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
                departureTimeText.text = getHoursFromDate(model.departure.date)
                departureAirportCodeText.text = model.departure.airport
                arrivalTimeText.text = getHoursFromDate(model.arrival.date)
                arrivalAirportCodeText.text = model.arrival.airport
                hasTransferText.visibility =
                    if (!model.hasTransfer) View.VISIBLE else View.INVISIBLE
                flightTimeSeparator.visibility =
                    if (!model.hasTransfer) View.VISIBLE else View.INVISIBLE
                flightTime.text = getFlightTime(model.departure.date, model.arrival.date)
            }
        }

        private fun formatPrice(price: Int): String {
            val formattedPrice = String.format(Locale.US, "%,d", price).replace(',', ' ')
            return "$formattedPrice ₽"
        }

        private fun getHoursFromDate(dateTimeString: String): String {
            val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
            val outputFormat = SimpleDateFormat("HH:mm", Locale.US)
            return try {
                outputFormat.format(isoFormat.parse(dateTimeString)!!)
            } catch (e: ParseException) {
                e.printStackTrace()
                ""
            }
        }

        private fun getFlightTime(firstDate: String, secondDate: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)

            val date1: Date? = dateFormat.parse(firstDate)
            val date2: Date? = dateFormat.parse(secondDate)
            try {
                val diffInMillis = date2!!.time - date1!!.time
                val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
                val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) - TimeUnit.HOURS.toMinutes(diffInHours)

                return "$diffInHours:$diffInMinutes ч в пути"
            } catch (e: ParseException) {
                return ""
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTicketsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AllTicketsViewHolder(
            AllTicketsItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllTicketsViewHolder, position: Int) {
        val ticket = getItem(position)
        holder.bind(ticket, holder.itemView.context)
    }

    companion object AllTicketsDiffCallback : DiffUtil.ItemCallback<TicketModel>() {
        override fun areItemsTheSame(oldItem: TicketModel, newItem: TicketModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TicketModel, newItem: TicketModel): Boolean {
            return oldItem == newItem
        }

    }

}