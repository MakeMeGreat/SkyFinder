package com.example.domain.usecase

import com.example.domain.Repository
import javax.inject.Inject

class GetTextFromSharedPrefsUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() = repository.getTextFromSharedPrefs()
}