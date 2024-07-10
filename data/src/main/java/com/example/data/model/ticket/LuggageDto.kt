package com.example.data.model.ticket

import com.example.data.model.PriceDto
import com.squareup.moshi.Json

data class LuggageDto(
    @Json(name = "has_luggage")
    val hasLuggage: Boolean,
    val price: PriceDto?,
)
