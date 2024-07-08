package com.example.skyfinder.presentation.ui.fragment

import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.skyfinder.databinding.FragmentMainBinding
import com.example.skyfinder.di.App
import com.example.skyfinder.presentation.ui.CyrillicInputFilter
import com.example.skyfinder.presentation.ui.MainActivity
import com.example.skyfinder.presentation.ui.adapter.MainOfferAdapter
import com.example.skyfinder.presentation.ui.adapter.MainOfferItemDecorator
import com.example.skyfinder.presentation.ui.viewmodel.MainFragmentViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: MainFragmentViewModel.MainFragmentViewModelFactory
    private lateinit var viewModel: MainFragmentViewModel

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.appComponent.inject(this)
        _binding = FragmentMainBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainFragmentViewModel::class.java]
        val adapter = MainOfferAdapter()
        binding.offerFeedRecyclerView.adapter = adapter
        binding.offerFeedRecyclerView.addItemDecoration(
            MainOfferItemDecorator(
                spaceBetween = 250,
                spaceSide = 30
            )
        )
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.offersStateFlow.collect {
                    adapter.submitList(it)
                    Log.d("myTest", "Data submitted to adapter $it")
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoadingStateFlow.collect { isLoading ->
                    binding.offersFeedProgressBar.visibility =
                        if (isLoading) View.VISIBLE else View.INVISIBLE
                }
            }
        }

        setupEditTexts()
//        val fm = (activity as MainActivity).supportFragmentManager
////        binding.toWhereEditText.setOnFocusChangeListener { _, hasFocus ->
////            if (hasFocus)
////            SearchBottomSheetFragment().show(fm, "my bottom sheet dialog")
////        }
//        binding.toWhereEditText.setOnClickListener {
//            binding.fromEditText.clearFocus()
//            SearchBottomSheetFragment().show(fm, "my bottom sheet dialog")
//        }
    }

    private fun setupEditTexts() {
        var job: Job? = null
        binding.fromEditText.apply {
            filters = arrayOf(InputFilter.LengthFilter(23), CyrillicInputFilter())
            setText(viewModel.getTextFromSharedPrefs())
            addTextChangedListener { editable ->
                job?.cancel()
                job = MainScope().launch {
                    delay(2000)
                    editable?.let {
                        viewModel.saveTextToSharedPrefs(editable.toString())
//                        if (editable.toString().isNotEmpty()) {
//                        }
                    }
                }
            }
        }
        val supportFragmentManager = (activity as MainActivity).supportFragmentManager
        binding.toWhereEditText.apply {
            filters = arrayOf(InputFilter.LengthFilter(23), CyrillicInputFilter())
            setOnClickListener {
                binding.fromEditText.clearFocus()
                SearchBottomSheetFragment().show(supportFragmentManager, "bottom sheet dialog")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}