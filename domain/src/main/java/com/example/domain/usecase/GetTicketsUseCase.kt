package com.example.domain.usecase

import com.example.domain.Repository
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.getTickets()
}