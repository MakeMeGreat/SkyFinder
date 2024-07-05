package com.example.domain.usecase

import com.example.domain.Repository
import javax.inject.Inject

class GetOffersUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.getOffers()
}