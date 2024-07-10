package com.example.data.model.ticketoffer

import com.example.data.model.PriceDto
import com.squareup.moshi.Json

data class TicketOfferDto(
    val id: Int,
    val title: String,
    @Json(name = "time_range")
    val timeRange: List<String>,
    val price: PriceDto,
)
