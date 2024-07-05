package com.example.domain.model.ticket

import com.example.domain.model.PriceDomainModel

data class TicketDomainModel(
    val id: Int,
    val badge: String?,
    val price: PriceDomainModel,
    val providerName: String,
    val company: String,
    val departure: DepartureDomainModel,
    val arrival: ArrivalDomainModel,
    val hasTransfer: Boolean,
    val hasVisaTransfer: Boolean,
    val luggage: LuggageDomainModel,
    val handLuggage: HandLuggageDomainModel,
    val isReturnable: Boolean,
    val isExchangeable: Boolean,
)
