package com.example.data.model.ticket

import com.squareup.moshi.Json

data class HandLuggageDto(
    @Json(name = "has_hand_luggage")
    val hasHandLuggage: Boolean,
    val size: String?,
)
