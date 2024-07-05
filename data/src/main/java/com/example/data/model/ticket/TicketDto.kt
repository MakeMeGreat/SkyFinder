package com.example.data.model.ticket

import com.example.data.model.PriceDto
import com.squareup.moshi.Json

data class TicketDto(
    val id: Int,
    val badge: String?,
    val price: PriceDto,
    @Json(name = "provider_name")
    val providerName: String,
    val company: String,
    val departure: DepartureDto,
    val arrival: ArrivalDto,
    @Json(name = "has_transfer")
    val hasTransfer: Boolean,
    @Json(name = "has_visa_transfer")
    val hasVisaTransfer: Boolean,
    val luggage: LuggageDto,
    @Json(name = "hand_luggage")
    val handLuggage: HandLuggageDto,
    @Json(name = "is_returnable")
    val isReturnable: Boolean,
    @Json(name = "is_exchangable")
    val isExchangeable: Boolean,
)