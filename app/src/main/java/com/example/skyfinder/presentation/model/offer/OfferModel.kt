package com.example.skyfinder.presentation.model.offer

import com.example.skyfinder.presentation.model.PriceModel


data class OfferModel(
    val id: Int,
    val title: String,
    val town: String,
    val price: PriceModel,
)
