package com.example.data.model.ticket

import com.example.data.model.PriceDto

data class LuggageDto(
    val hasLuggage: Boolean,
    val price: PriceDto?,
)
