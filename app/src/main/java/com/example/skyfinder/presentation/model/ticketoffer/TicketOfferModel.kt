package com.example.skyfinder.presentation.model.ticketoffer

import com.example.skyfinder.presentation.model.PriceModel

data class TicketOfferModel(
    val id: Int,
    val title: String,
    val timeRange: List<String>,
    val price: PriceModel,
)
