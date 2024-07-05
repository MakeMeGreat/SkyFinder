package com.example.data.network

import com.example.data.model.offer.OfferResponse
import com.example.data.model.ticket.TicketResponse
import com.example.data.model.ticketoffer.TicketOfferResponse
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val firstAPI: FirstAPI,
    private val secondAPI: SecondAPI,
) {
    suspend fun getOffers(): OfferResponse {
        return firstAPI.getOffers()
    }

    suspend fun getTicketOffers(): TicketOfferResponse {
        return firstAPI.getTicketOffers()
    }

    suspend fun getTickets(): TicketResponse {
        return secondAPI.getTickets()
    }
}