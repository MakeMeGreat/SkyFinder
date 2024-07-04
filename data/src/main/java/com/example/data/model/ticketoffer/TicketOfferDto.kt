package com.example.data.model.ticketoffer

import com.example.data.model.PriceDto

data class TicketOfferDto(
    val id: Int,
    val title: String,
    val timeRange: List<String>,
    val price: PriceDto,
)
