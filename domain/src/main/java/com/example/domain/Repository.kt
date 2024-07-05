package com.example.domain

import com.example.domain.model.offer.OfferResponseDomainModel
import com.example.domain.model.ticket.TicketResponseDomainModel
import com.example.domain.model.ticketoffer.TicketOfferResponseDomainModel

interface Repository {

    suspend fun getOffers(): OfferResponseDomainModel

    suspend fun getTickets(): TicketResponseDomainModel

    suspend fun getTicketsOffers(): TicketOfferResponseDomainModel
}