package com.example.data.network

import com.example.data.mapper.DataToDomainMapper
import com.example.domain.Repository
import com.example.domain.model.offer.OfferResponseDomainModel
import com.example.domain.model.ticket.TicketResponseDomainModel
import com.example.domain.model.ticketoffer.TicketOfferResponseDomainModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val toDomainMapper: DataToDomainMapper,
): Repository {
    override suspend fun getOffers(): OfferResponseDomainModel {
        return toDomainMapper.mapOfferResponseToDomainModel(networkDataSource.getOffers())
    }

    override suspend fun getTickets(): TicketResponseDomainModel {
        return toDomainMapper.mapTicketResponseToDomainModel(networkDataSource.getTickets())
    }

    override suspend fun getTicketsOffers(): TicketOfferResponseDomainModel {
        return toDomainMapper.mapTicketOfferResponseToDomainModel(networkDataSource.getTicketOffers())
    }
}