package com.example.skyfinder.presentation.model.ticket

import com.example.skyfinder.presentation.model.PriceModel

data class LuggageModel(
    val hasLuggage: Boolean,
    val price: PriceModel?,
)
