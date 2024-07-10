package com.example.domain.model.offer

import com.example.domain.model.PriceDomainModel

data class OfferDomainModel(
    val id: Int,
    val title: String,
    val town: String,
    val price: PriceDomainModel,
)
