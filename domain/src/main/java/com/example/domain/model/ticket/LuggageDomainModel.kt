package com.example.domain.model.ticket

import com.example.domain.model.PriceDomainModel

data class LuggageDomainModel(
    val hasLuggage: Boolean,
    val price: PriceDomainModel?,
)
