package com.example.domain.model.ticketoffer

import com.example.domain.model.PriceDomainModel

data class TicketOfferDomainModel(
    val id: Int,
    val title: String,
    val timeRange: List<String>,
    val price: PriceDomainModel
)
