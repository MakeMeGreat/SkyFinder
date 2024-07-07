package com.example.domain.usecase

import com.example.domain.Repository
import javax.inject.Inject

class SaveTextToSharedPrefsUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(text: String) = repository.saveTextToSharedPrefs(text)
}