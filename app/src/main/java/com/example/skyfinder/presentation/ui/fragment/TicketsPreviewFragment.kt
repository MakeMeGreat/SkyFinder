package com.example.skyfinder.presentation.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.skyfinder.R
import com.example.skyfinder.databinding.FragmentTicketsPreviewBinding
import com.example.skyfinder.di.App
import com.example.skyfinder.presentation.ui.adapter.DirectFlightsAdapter
import com.example.skyfinder.presentation.ui.viewmodel.TicketsPreviewViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val FROM_CITY_NAME = "FROM_CITY_NAME"
private const val TO_WHERE_CITY_NAME = "TO_WHERE_CITY_NAME"

class TicketsPreviewFragment : Fragment() {

    private var _binding: FragmentTicketsPreviewBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: TicketsPreviewViewModel.TicketsPreviewViewModelFactory
    private lateinit var viewModel: TicketsPreviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.appComponent.inject(this)
        _binding = FragmentTicketsPreviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = createViewModel()
        setupEditTexts()
        setupDirectFlightsRecyclerView()
        setupSwitchButton()
        setupClearButton()
        setupBackButton()
        makeDepartureChipToObserveVMFlow()
        makeDepartureChipDatePickable()
        makeReturnChipToObserveVMFlow()
        makeReturnChipDatePickable()
        setupSeeAllTicketsButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSeeAllTicketsButton() {
        binding.seeAllTicketsButton.setOnClickListener {
            findNavController().navigate(R.id.action_ticketsPreviewFragment_to_allTicketsFragment)
        }
    }

    private fun makeReturnChipDatePickable() {
        binding.returnDateChip.setOnClickListener {
            showDatePickerDialog { selectedDate ->
                viewModel.updateReturnDate(selectedDate)
            }
        }
    }

    private fun makeDepartureChipDatePickable() {
        binding.departureDateChip.setOnClickListener {
            showDatePickerDialog { selectedDate ->
                viewModel.updateDepartureDate(selectedDate)
            }
        }
    }

    private fun makeReturnChipToObserveVMFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.returnDateStateFlow.collect { date ->
                    if (date.time != 0L) {
                        binding.returnDateChip.text = formatLongToDate(date)
                        binding.returnDateChip.chipIcon = null
                    } else {
                        binding.returnDateChip.chipIcon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.add_icon)
                    }
                }
            }
        }
    }

    private fun makeDepartureChipToObserveVMFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.departureDateStateFlow.collect {
                    binding.departureDateChip.text = formatLongToDate(it)
                }
            }
        }
    }

    private fun showDatePickerDialog(onDateSelected: (Date) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                onDateSelected(selectedDate.time)
//                viewModel.updateDepartureDate(selectedDate.time)
            }, year, month, day
        )
        datePickerDialog.show()
    }

    private fun formatLongToDate(dateInLong: Date): String {
        val dateFormat = SimpleDateFormat("dd MMM, E", Locale("ru"))
        return dateFormat.format(dateInLong)
    }

    private fun setupEditTexts() {
        setupEditTextsActionButton()
        makeEditTextsToObserveViewModelValues()
    }

    private fun setupEditTextsActionButton() {
        binding.previewFromEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                viewModel.setFromCityName(binding.previewFromEditText.text.toString())
            }
            true
        }
        binding.previewToWhereEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.setToWhereCiteName(binding.previewToWhereEditText.text.toString())
            }
            true
        }
    }

    private fun makeEditTextsToObserveViewModelValues() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fromCityNameStateFlow.collect {
                    binding.previewFromEditText.setText(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toWhereCityNameStateFlow.collect {
                    binding.previewToWhereEditText.setText(it)
                }
            }
        }
    }

    private fun setupBackButton() {
        binding.backArrowButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupClearButton() {
        binding.clearImageButton.setOnClickListener {
            viewModel.clearToWhereCityName()
        }
    }

    private fun setupSwitchButton() {
        binding.switchImageButton.setOnClickListener {
            viewModel.switchCityNames()
        }
    }

    private fun setupDirectFlightsRecyclerView() {
        val adapter = DirectFlightsAdapter()
        binding.directFlightsRecyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.ticketOffersStateFlow.collect {
                    adapter.submitList(it.take(3))
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoadingStateFlow.collect {
                    viewModel.isLoadingStateFlow.collect { isLoading ->
                        binding.directFlightsRecyclerProgressBar.visibility =
                            if (isLoading) View.VISIBLE else View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun createViewModel(): TicketsPreviewViewModel {
        val args: TicketsPreviewFragmentArgs by navArgs()
        val fromCityName = args.fromCityName
        val toWhereCityName = args.toWhereCityName
        viewModelFactory.fromCityNameString = fromCityName
        viewModelFactory.toWhereCityNameString = toWhereCityName
        return ViewModelProvider(this, viewModelFactory)[TicketsPreviewViewModel::class.java]
    }

//    companion object {
//        fun newInstance(fromCityName: String, toWhereCityName: String) =
//            TicketsPreviewFragment().apply {
//                arguments = Bundle().apply {
//                    putString(FROM_CITY_NAME, fromCityName)
//                    putString(TO_WHERE_CITY_NAME, toWhereCityName)
//                }
//            }
//    }
}