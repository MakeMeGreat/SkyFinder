package com.example.skyfinder.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.navigation.fragment.findNavController
import com.example.skyfinder.R
import com.example.skyfinder.databinding.SearchBottomSheetFragmentBinding
import com.example.skyfinder.presentation.model.SearchRecommendationItem
import com.example.skyfinder.presentation.ui.MainActivity
import com.example.skyfinder.presentation.ui.adapter.SearchRecommendationAdapter
import com.example.skyfinder.presentation.ui.fragment.stub.Stub1Fragment
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
    ): View? {
        _binding = SearchBottomSheetFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SearchRecommendationAdapter { item ->
            binding.searchToWhereEditText.setText(item.city)
        }
        binding.searchRecommendationRecyclerView.adapter = adapter
        adapter.submitList(recommendationItemList)
        val fromCityName = requireArguments().getString(CITY_KEY)
        binding.searchFromEditText.setText(fromCityName)
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

    private fun setupRouteButtons() {
        binding.apply {
            difficultRouteButton.setOnClickListener { closeBottomSheetAndOpenStubFragment() }
            weekendsButton.setOnClickListener { closeBottomSheetAndOpenStubFragment() }
            hotTicketsButton.setOnClickListener { closeBottomSheetAndOpenStubFragment() }
            anywhereButton.setOnClickListener {
                binding.searchToWhereEditText.setText("Куда угодно")
            }
        }
    }

    private fun closeBottomSheetAndOpenStubFragment() {
        this@SearchBottomSheetFragment.dismiss()
        findNavController().navigate(R.id.action_main_fragment_to_stub5Fragment)
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