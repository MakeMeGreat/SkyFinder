package com.example.data.model.ticket

import com.example.data.model.PriceDto

data class TicketDto(
    val id: Int,
    val badge: String?,
    val price: PriceDto,
    val providerName: String,
    val company: String,
    val departure: DepartureDto,
    val arrival: ArrivalDto,
    val hasTransfer: Boolean,
    val hasVisaTransfer: Boolean,
    val luggage: LuggageDto,
    val handLuggage: HandLuggageDto,
    val isReturnable: Boolean,
    val isExchangeable: Boolean,
)