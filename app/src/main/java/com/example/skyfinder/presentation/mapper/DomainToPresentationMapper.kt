package com.example.skyfinder.presentation.mapper


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
import com.example.skyfinder.presentation.model.PriceModel
import com.example.skyfinder.presentation.model.offer.OfferModel
import com.example.skyfinder.presentation.model.offer.OfferResponseModel
import com.example.skyfinder.presentation.model.ticket.ArrivalModel
import com.example.skyfinder.presentation.model.ticket.DepartureModel
import com.example.skyfinder.presentation.model.ticket.HandLuggageModel
import com.example.skyfinder.presentation.model.ticket.LuggageModel
import com.example.skyfinder.presentation.model.ticket.TicketModel
import com.example.skyfinder.presentation.model.ticket.TicketResponseModel
import com.example.skyfinder.presentation.model.ticketoffer.TicketOfferModel
import com.example.skyfinder.presentation.model.ticketoffer.TicketOfferResponseModel
import javax.inject.Inject

class DomainToPresentationMapper @Inject constructor() {

    fun mapOfferResponseDomainToPresentationModel(offersResponseDomainModel: OfferResponseDomainModel): OfferResponseModel {
        return OfferResponseModel(
            offers = offersResponseDomainModel.offers.map(::mapOfferDomainToPresentationModel)
        )
    }

    private fun mapOfferDomainToPresentationModel(offerDomainModel: OfferDomainModel) = OfferModel(
        id = offerDomainModel.id,
        title = offerDomainModel.title,
        town = offerDomainModel.town,
        price = mapPriceDomainToPresentationModel(offerDomainModel.price)
    )

    fun mapTicketOfferResponseDomainToPresentationModel(ticketOfferResponseDomainModel: TicketOfferResponseDomainModel): TicketOfferResponseModel {
        return TicketOfferResponseModel(
            ticketsOffers = ticketOfferResponseDomainModel.ticketsOffers.map(::mapTicketOfferDomainToPresentationModel)
        )
    }

    private fun mapTicketOfferDomainToPresentationModel(ticketOfferDomainModel: TicketOfferDomainModel) =
        TicketOfferModel(
            id = ticketOfferDomainModel.id,
            title = ticketOfferDomainModel.title,
            timeRange = ticketOfferDomainModel.timeRange,
            price = mapPriceDomainToPresentationModel(ticketOfferDomainModel.price)
        )

    fun mapTicketResponseDomainToPresentationModel(ticketResponseDomainModel: TicketResponseDomainModel): TicketResponseModel {
        return TicketResponseModel(
            tickets = ticketResponseDomainModel.tickets.map(::mapTicketDomainToPresentationModel)
        )
    }

    private fun mapTicketDomainToPresentationModel(ticketDomainModel: TicketDomainModel) =
        TicketModel(
            id = ticketDomainModel.id,
            badge = ticketDomainModel.badge,
            price = mapPriceDomainToPresentationModel(ticketDomainModel.price),
            providerName = ticketDomainModel.providerName,
            company = ticketDomainModel.company,
            departure = mapDepartureDomainToPresentationModel(ticketDomainModel.departure),
            arrival = mapArrivalDomainToPresentationModel(ticketDomainModel.arrival),
            hasTransfer = ticketDomainModel.hasTransfer,
            hasVisaTransfer = ticketDomainModel.hasVisaTransfer,
            luggage = mapLuggageDomainToPresentationModel(ticketDomainModel.luggage),
            handLuggage = mapHandLuggageDomainToPresentationModel(ticketDomainModel.handLuggage),
            isReturnable = ticketDomainModel.isReturnable,
            isExchangeable = ticketDomainModel.isExchangeable,
        )

    private fun mapDepartureDomainToPresentationModel(departureDomainModel: DepartureDomainModel) =
        DepartureModel(
            town = departureDomainModel.town,
            date = departureDomainModel.date,
            airport = departureDomainModel.airport,
        )

    private fun mapArrivalDomainToPresentationModel(arrivalDomainModel: ArrivalDomainModel) =
        ArrivalModel(
            town = arrivalDomainModel.town,
            date = arrivalDomainModel.date,
            airport = arrivalDomainModel.airport,
        )

    private fun mapLuggageDomainToPresentationModel(luggageDomainModel: LuggageDomainModel) =
        LuggageModel(
            hasLuggage = luggageDomainModel.hasLuggage,
            price = luggageDomainModel.price?.let { mapPriceDomainToPresentationModel(it) }
        )

    private fun mapHandLuggageDomainToPresentationModel(handLuggageDomainModel: HandLuggageDomainModel) =
        HandLuggageModel(
            hasHandLuggage = handLuggageDomainModel.hasHandLuggage,
            size = handLuggageDomainModel.size
        )


    private fun mapPriceDomainToPresentationModel(priceDomainModel: PriceDomainModel) =
        PriceModel(value = priceDomainModel.value)
}