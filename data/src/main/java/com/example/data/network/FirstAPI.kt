package com.example.data.network

import com.example.data.model.offer.OfferResponse
import com.example.data.model.ticketoffer.TicketOfferResponse
import retrofit2.http.GET

interface FirstAPI {

    @GET("u/0/uc?id=1o1nX3uFISrG1gR-jr_03Qlu4_KEZWhav&export=download")
    suspend fun getOffers(): OfferResponse

    @GET("u/0/uc?id=13WhZ5ahHBwMiHRXxWPq-bYlRVRwAujta&export=download")
    suspend fun getTicketOffers(): TicketOfferResponse
}