package com.example.skyfinder.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import com.example.skyfinder.R
import com.example.skyfinder.databinding.SearchBottomSheetFragmentBinding
import com.example.skyfinder.presentation.model.SearchRecommendationItem
import com.example.skyfinder.presentation.ui.adapter.SearchRecommendationAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val CITY_KEY = "CITY_KEY"

class SearchBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: SearchBottomSheetFragmentBinding? = null
    private val binding get() = _binding!!

    private val recommendationItemList = listOf(
        SearchRecommendationItem(id = 1, city = "Стамбул", reason = "Популярное направление"),
        SearchRecommendationItem(id = 2, city = "Сочи", reason = "Популярное направление"),
        SearchRecommendationItem(id = 3, city = "Пхукет", reason = "Популярное направление")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchBottomSheetFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.post {
            val parent = view.parent as View
            val behavior = BottomSheetBehavior.from(parent)
            behavior.peekHeight = (parent.height)
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        val adapter = SearchRecommendationAdapter { item ->
            binding.searchToWhereEditText.setText(item.city)
            navigateToTicketsPreviewFragment()
        }
        binding.searchRecommendationRecyclerView.adapter = adapter
        adapter.submitList(recommendationItemList)
        val fromCityName = requireArguments().getString(CITY_KEY)
        binding.searchFromEditText.setText(fromCityName)
        setupEditTextActionButton()
        setupClearButton()
        setupRouteButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClearButton() {
        binding.clearImageButton.setOnClickListener {
            binding.searchToWhereEditText.text.clear()
        }
    }

    private fun setupEditTextActionButton() {
        binding.searchToWhereEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH && isCitiesAreNotEmpty()) {
                navigateToTicketsPreviewFragment()
            }
            true
        }
    }

    private fun isCitiesAreNotEmpty(): Boolean {
        return binding.searchFromEditText.text.isNotEmpty() &&
                binding.searchToWhereEditText.text.isNotEmpty()
    }

    private fun setupRouteButtons() {
        binding.apply {
            difficultRouteButton.setOnClickListener { navigateToStubFragment() }
            weekendsButton.setOnClickListener { navigateToStubFragment() }
            hotTicketsButton.setOnClickListener { navigateToStubFragment() }
            anywhereButton.setOnClickListener {
                binding.searchToWhereEditText.setText(R.string.anywhere_text)
                navigateToTicketsPreviewFragment()
            }
        }
    }

    private fun navigateToStubFragment() {
        this@SearchBottomSheetFragment.dismiss()
        findNavController().navigate(R.id.action_main_fragment_to_stub5Fragment)
    }

    private fun navigateToTicketsPreviewFragment() {
        this@SearchBottomSheetFragment.dismiss()
        val action = MainFragmentDirections.actionMainFragmentToTicketsPreviewFragment(
            binding.searchFromEditText.text.toString(),
            binding.searchToWhereEditText.text.toString()
        )
        findNavController().navigate(action)
    }

    companion object {
        fun newInstance(fromCityName: String): SearchBottomSheetFragment {
            return SearchBottomSheetFragment().apply {
                arguments = Bundle().also {
                    it.putString(CITY_KEY, fromCityName)
                }
            }
        }
    }
}