package com.example.skyfinder.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetTicketsUseCase
import com.example.skyfinder.presentation.mapper.DomainToPresentationMapper
import com.example.skyfinder.presentation.model.ticket.TicketModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.properties.Delegates

class AllTicketsViewModel @Inject constructor(
    private val fromCityName: String,
    private val toWhereCityName: String,
    private val departureDateLong: Long,
    private val getTicketsUseCase: GetTicketsUseCase,
    private val mapper: DomainToPresentationMapper,
) : ViewModel() {

    private val _ticketsStateFlow = MutableStateFlow<List<TicketModel>>(emptyList())
    val ticketsStateFlow: StateFlow<List<TicketModel>> = _ticketsStateFlow

    private val _isLoadingStateFlow = MutableStateFlow(false)
    val isLoadingStateFlow: StateFlow<Boolean> = _isLoadingStateFlow

    private val _routeStateFlow = MutableStateFlow("$fromCityName-$toWhereCityName")
    val routeStateFlow: StateFlow<String> = _routeStateFlow

    private val _departureDetailsStateFlow = MutableStateFlow(getDepartureDetails(departureDateLong))
    val departureDetailsStateFlow: StateFlow<String> = _departureDetailsStateFlow

    init {
        getTickets()
    }

    private fun getTickets() {
        _isLoadingStateFlow.value = true
        viewModelScope.launch {
            try {
                val ticketResponse = getTicketsUseCase()
                mapper.mapTicketResponseDomainToPresentationModel(ticketResponse)
                    .also {
                        _ticketsStateFlow.value = it.tickets
                        _isLoadingStateFlow.value = false
                    }
            } catch (e: Exception) {
                _isLoadingStateFlow.value = false
                Log.e("TAG", "${e.printStackTrace()}")
            }
        }
    }

    private fun getDepartureDetails(long: Long): String {
        val date = formatDateToString(Date(long))
        return "$date, 1 пассажир"
    }


    private fun formatDateToString(date: Date): String {
        val dateFormat = SimpleDateFormat("d MMMM", Locale("ru"))
        return dateFormat.format(date)
    }

    class AllTicketsViewModelFactory @Inject constructor(
        private val getTicketsUseCase: GetTicketsUseCase,
        private val mapper: DomainToPresentationMapper,
    ) : ViewModelProvider.Factory {

        lateinit var fromCityNameString: String
        lateinit var toWhereCityNameString: String
        var departureDateLong by Delegates.notNull<Long>()

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllTicketsViewModel(
                fromCityName = fromCityNameString,
                toWhereCityName = toWhereCityNameString,
                departureDateLong = departureDateLong,
                getTicketsUseCase = getTicketsUseCase,
                mapper = mapper
            ) as T
        }
    }
}