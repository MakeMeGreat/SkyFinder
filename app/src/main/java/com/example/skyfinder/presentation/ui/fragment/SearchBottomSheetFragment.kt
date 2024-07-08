package com.example.skyfinder.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.skyfinder.databinding.SearchBottomSheetFragmentBinding
import com.example.skyfinder.presentation.model.SearchRecommendationItem
import com.example.skyfinder.presentation.ui.adapter.SearchRecommendationAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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
        binding.difficultRouteButton.setOnClickListener {
            Toast.makeText(context, "click", LENGTH_SHORT).show()
        }
        val adapter = SearchRecommendationAdapter()
        binding.searchRecommendationRecyclerView.adapter = adapter
        adapter.submitList(recommendationItemList)
    }
}