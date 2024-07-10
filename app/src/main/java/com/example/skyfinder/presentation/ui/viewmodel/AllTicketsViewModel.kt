package com.example.skyfinder.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetTicketsUseCase
import com.example.skyfinder.presentation.mapper.DomainToPresentationMapper
import com.example.skyfinder.presentation.model.offer.OfferModel
import com.example.skyfinder.presentation.model.ticket.TicketModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllTicketsViewModel @Inject constructor(
    private val getTicketsUseCase: GetTicketsUseCase,
    private val mapper: DomainToPresentationMapper,
) : ViewModel() {

    private var _ticketsStateFlow = MutableStateFlow<List<TicketModel>>(emptyList())
    val ticketsStateFlow: StateFlow<List<TicketModel>> = _ticketsStateFlow

    private var _isLoadingStateFlow = MutableStateFlow(false)
    val isLoadingStateFlow: StateFlow<Boolean> = _isLoadingStateFlow

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

    class AllTicketsViewModelFactory @Inject constructor(
        private val getTicketsUseCase: GetTicketsUseCase,
        private val mapper: DomainToPresentationMapper,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllTicketsViewModel(
                getTicketsUseCase = getTicketsUseCase,
                mapper = mapper
            ) as T
        }
    }
}