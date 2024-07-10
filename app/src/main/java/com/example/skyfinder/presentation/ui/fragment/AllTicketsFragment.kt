package com.example.skyfinder.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.skyfinder.R
import com.example.skyfinder.databinding.FragmentAllTicketsBinding
import com.example.skyfinder.databinding.FragmentTicketsPreviewBinding
import com.example.skyfinder.di.App
import com.example.skyfinder.presentation.ui.adapter.AllTicketsAdapter
import com.example.skyfinder.presentation.ui.adapter.AllTicketsDecorator
import com.example.skyfinder.presentation.ui.viewmodel.AllTicketsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class AllTicketsFragment : Fragment() {

    private var _binding: FragmentAllTicketsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: AllTicketsViewModel.AllTicketsViewModelFactory
    private lateinit var viewModel: AllTicketsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.appComponent.inject(this)
        _binding = FragmentAllTicketsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = createViewModel()
        setupRecyclerView()
        showDataFromPreviousFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDataFromPreviousFragment() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.routeStateFlow .collect{
                    binding.routeNameText.text = it
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.departureDetailsStateFlow .collect{
                    binding.routeDetailsText.text = it
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val adapter = AllTicketsAdapter()
        binding.ticketsRecyclerView.adapter = adapter
        binding.ticketsRecyclerView.addItemDecoration(
            AllTicketsDecorator(40)
        )
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.ticketsStateFlow.collect{
                    adapter.submitList(it)
                }
            }
        }

        setupRecyclerProgressBar()
    }

    private fun setupRecyclerProgressBar() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoadingStateFlow.collect{ isLoading ->
                    binding.ticketsProgressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
                }
            }
        }
    }

    private fun createViewModel(): AllTicketsViewModel {
        val args: AllTicketsFragmentArgs by navArgs()
        val fromCityName = args.fromCityName
        val toWhereCityName = args.toWhereCityName
        val dateLong = args.departureDate
        viewModelFactory.fromCityNameString = fromCityName
        viewModelFactory.toWhereCityNameString = toWhereCityName
        viewModelFactory.departureDateLong = dateLong
        return ViewModelProvider(this, viewModelFactory)[AllTicketsViewModel::class.java]
    }

    companion object {
//        fun newInstance(param1: String, param2: String) =
//            AllTicketsFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
}