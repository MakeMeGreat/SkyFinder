package com.example.skyfinder.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetTicketsOffersUseCase
import com.example.skyfinder.presentation.mapper.DomainToPresentationMapper
import com.example.skyfinder.presentation.model.ticketoffer.TicketOfferModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class TicketsPreviewViewModel @Inject constructor(
    private val fromCityName: String,
    private val toWhereCityName: String,
    private val getTicketsOffersUseCase: GetTicketsOffersUseCase,
    private val mapper: DomainToPresentationMapper,
) : ViewModel() {

    private val _fromCityNameStateFlow = MutableStateFlow(fromCityName)
    val fromCityNameStateFlow: StateFlow<String> = _fromCityNameStateFlow

    private val _toWhereCityNameStateFlow = MutableStateFlow(toWhereCityName)
    val toWhereCityNameStateFlow: StateFlow<String> = _toWhereCityNameStateFlow

    private val _ticketOffersStateFlow = MutableStateFlow(emptyList<TicketOfferModel>())
    val ticketOffersStateFlow: StateFlow<List<TicketOfferModel>> = _ticketOffersStateFlow

    private var _isLoadingStateFlow = MutableStateFlow(false)
    val isLoadingStateFlow: StateFlow<Boolean> = _isLoadingStateFlow

    private val _departureDateStateFlow = MutableStateFlow(Date())
    val departureDateStateFlow: StateFlow<Date> = _departureDateStateFlow

    private val _returnDateStateFlow = MutableStateFlow(Date(0))
    val returnDateStateFlow: StateFlow<Date> = _returnDateStateFlow

    init {
        getTicketOffers()
    }

    private fun getTicketOffers() {
        _isLoadingStateFlow.value = true
        viewModelScope.launch {
            try {
                val ticketOfferResponse = getTicketsOffersUseCase()
                mapper.mapTicketOfferResponseDomainToPresentationModel(ticketOfferResponse)
                    .also {
                        _ticketOffersStateFlow.value = it.ticketsOffers
                        _isLoadingStateFlow.value = false
                    }
            } catch (e: Exception) {
                _isLoadingStateFlow.value = false
                Log.e("TAG", "${e.printStackTrace()}")
            }
        }
    }

    fun updateReturnDate(date: Date) {
        _returnDateStateFlow.value = date
    }

    fun updateDepartureDate(date: Date) {
        _departureDateStateFlow.value = date
    }

    fun setFromCityName(cityName: String) {
        _fromCityNameStateFlow.value = cityName
    }

    fun setToWhereCiteName(cityName: String) {
        _toWhereCityNameStateFlow.value = cityName
    }

    fun switchCityNames() {
        val firstName = _fromCityNameStateFlow.value
        _fromCityNameStateFlow.value = _toWhereCityNameStateFlow.value.also {
            _toWhereCityNameStateFlow.value = firstName
        }
    }

    fun clearToWhereCityName() {
        _toWhereCityNameStateFlow.value = ""
    }


    class TicketsPreviewViewModelFactory @Inject constructor(
        private val getTicketsOffersUseCase: GetTicketsOffersUseCase,
        private val mapper: DomainToPresentationMapper,
    ) : ViewModelProvider.Factory {

        lateinit var fromCityNameString: String
        lateinit var toWhereCityNameString: String

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TicketsPreviewViewModel(
                fromCityName = fromCityNameString,
                toWhereCityName = toWhereCityNameString,
                getTicketsOffersUseCase = getTicketsOffersUseCase,
                mapper = mapper,
            ) as T
        }
    }

}