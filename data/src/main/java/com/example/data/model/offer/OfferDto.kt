package com.example.data.model.offer

import com.example.data.model.PriceDto

data class OfferDto(
    val id: Int,
    val title: String,
    val town: String,
    val price: PriceDto,
)
