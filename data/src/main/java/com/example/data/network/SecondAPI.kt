package com.example.data.network

import com.example.data.model.ticket.TicketResponse
import retrofit2.http.GET

interface SecondAPI {

    @GET("uc?export=download&id=1HogOsz4hWkRwco4kud3isZHFQLUAwNBA")
    suspend fun getTickets(): TicketResponse
}