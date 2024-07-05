package com.example.data.model.ticketoffer

import com.squareup.moshi.Json

data class TicketOfferResponse(
    @Json(name = "tickets_offers")
    val ticketsOffers: List<TicketOfferDto>,
)
