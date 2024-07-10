package com.example.data.mapper

import com.example.data.model.PriceDto
import com.example.data.model.offer.OfferDto
import com.example.data.model.offer.OfferResponse
import com.example.data.model.ticket.ArrivalDto
import com.example.data.model.ticket.DepartureDto
import com.example.data.model.ticket.HandLuggageDto
import com.example.data.model.ticket.LuggageDto
import com.example.data.model.ticket.TicketDto
import com.example.data.model.ticket.TicketResponse
import com.example.data.model.ticketoffer.TicketOfferDto
import com.example.data.model.ticketoffer.TicketOfferResponse
import com.example.domain.model.PriceDomainModel
import com.example.domain.model.offer.OfferDomainModel
import com.example.domain.model.offer.OfferResponseDomainModel
import com.example.domain.model.ticket.ArrivalDomainModel
import com.example.domain.model.ticket.DepartureDomainModel
import com.example.domain.model.ticket.HandLuggageDomainModel
import com.example.domain.model.ticket.LuggageDomainModel
import com.example.domain.model.ticket.TicketDomainModel
import com.example.domain.model.ticket.TicketResponseDomainModel
import com.example.domain.model.ticketoffer.TicketOfferDomainModel
import com.example.domain.model.ticketoffer.TicketOfferResponseDomainModel
import javax.inject.Inject

class DataToDomainMapper @Inject constructor() {

    fun mapOfferResponseToDomainModel(offersResponse: OfferResponse): OfferResponseDomainModel {
        return OfferResponseDomainModel(
            offers = offersResponse.offers.map(::mapOfferDtoToDomainModel)
        )
    }

    private fun mapOfferDtoToDomainModel(offerDto: OfferDto) = OfferDomainModel(
        id = offerDto.id,
        title = offerDto.title,
        town = offerDto.town,
        price = mapPriceDtoToDomainModel(offerDto.price)
    )

    fun mapTicketOfferResponseToDomainModel(ticketOfferResponse: TicketOfferResponse): TicketOfferResponseDomainModel {
        return TicketOfferResponseDomainModel(
            ticketsOffers = ticketOfferResponse.ticketsOffers.map(::mapTicketOfferDtoToDomainModel)
        )
    }

    private fun mapTicketOfferDtoToDomainModel(ticketOfferDto: TicketOfferDto) =
        TicketOfferDomainModel(
            id = ticketOfferDto.id,
            title = ticketOfferDto.title,
            timeRange = ticketOfferDto.timeRange,
            price = mapPriceDtoToDomainModel(ticketOfferDto.price)
        )

    fun mapTicketResponseToDomainModel(ticketResponse: TicketResponse): TicketResponseDomainModel {
        return TicketResponseDomainModel(
            tickets = ticketResponse.tickets.map(::mapTicketDtoToDomainModel)
        )
    }

    private fun mapTicketDtoToDomainModel(ticketDto: TicketDto) = TicketDomainModel(
        id = ticketDto.id,
        badge = ticketDto.badge,
        price = mapPriceDtoToDomainModel(ticketDto.price),
        providerName = ticketDto.providerName,
        company = ticketDto.company,
        departure = mapDepartureDtoToDomainModel(ticketDto.departure),
        arrival = mapArrivalDtoToDomainModel(ticketDto.arrival),
        hasTransfer = ticketDto.hasTransfer,
        hasVisaTransfer = ticketDto.hasVisaTransfer,
        luggage = mapLuggageDtoToDomainModel(ticketDto.luggage),
        handLuggage = mapHandLuggageDtoToDomainModel(ticketDto.handLuggage),
        isReturnable = ticketDto.isReturnable,
        isExchangeable = ticketDto.isExchangeable,
    )

    private fun mapDepartureDtoToDomainModel(departureDto: DepartureDto) = DepartureDomainModel(
        town = departureDto.town,
        date = departureDto.date,
        airport = departureDto.airport,
    )

    private fun mapArrivalDtoToDomainModel(arrivalDto: ArrivalDto) = ArrivalDomainModel(
        town = arrivalDto.town,
        date = arrivalDto.date,
        airport = arrivalDto.airport,
    )

    private fun mapLuggageDtoToDomainModel(luggageDto: LuggageDto) = LuggageDomainModel(
        hasLuggage = luggageDto.hasLuggage,
        price = luggageDto.price?.let { mapPriceDtoToDomainModel(it) }
    )

    private fun mapHandLuggageDtoToDomainModel(handLuggageDto: HandLuggageDto) =
        HandLuggageDomainModel(
            hasHandLuggage = handLuggageDto.hasHandLuggage,
            size = handLuggageDto.size
        )


    private fun mapPriceDtoToDomainModel(priceDto: PriceDto) =
        PriceDomainModel(value = priceDto.value)
}