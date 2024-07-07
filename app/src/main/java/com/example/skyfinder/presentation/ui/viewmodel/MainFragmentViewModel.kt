package com.example.skyfinder.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetOffersUseCase
import com.example.domain.usecase.GetTextFromSharedPrefsUseCase
import com.example.domain.usecase.SaveTextToSharedPrefsUseCase
import com.example.skyfinder.presentation.mapper.DomainToPresentationMapper
import com.example.skyfinder.presentation.model.offer.OfferModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val getOffersUseCase: GetOffersUseCase,
    private val mapper: DomainToPresentationMapper,
    private val saveTextToSharedPrefsUseCase: SaveTextToSharedPrefsUseCase,
    private val getTextFromSharedPrefsUseCase: GetTextFromSharedPrefsUseCase,
) : ViewModel() {

    private var _offersStateFlow = MutableStateFlow<List<OfferModel>>(emptyList())
    val offersStateFlow: StateFlow<List<OfferModel>> = _offersStateFlow

    private var _isLoadingStateFlow = MutableStateFlow(false)
    val isLoadingStateFlow: StateFlow<Boolean> = _isLoadingStateFlow

    init {
        getOffers()
    }

    private fun getOffers() {
        _isLoadingStateFlow.value = true
        viewModelScope.launch {
            val offerResponse = getOffersUseCase()
            mapper.mapOfferResponseDomainToPresentationModel(offerResponse)
                .also {
                    _offersStateFlow.value = it.offers
                    _isLoadingStateFlow.value = false
                }
        }
    }

    fun getTextFromSharedPrefs(): String = getTextFromSharedPrefsUseCase()

    fun saveTextToSharedPrefs(text: String) = saveTextToSharedPrefsUseCase(text)

    class MainFragmentViewModelFactory @Inject constructor(
        private val getOffersUseCase: GetOffersUseCase,
        private val domainToPresentationMapper: DomainToPresentationMapper,
        private val saveTextToSharedPrefsUseCase: SaveTextToSharedPrefsUseCase,
        private val getTextFromSharedPrefsUseCase: GetTextFromSharedPrefsUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainFragmentViewModel(
                getOffersUseCase = getOffersUseCase,
                mapper = domainToPresentationMapper,
                saveTextToSharedPrefsUseCase = saveTextToSharedPrefsUseCase,
                getTextFromSharedPrefsUseCase = getTextFromSharedPrefsUseCase,
            ) as T
        }
    }
}