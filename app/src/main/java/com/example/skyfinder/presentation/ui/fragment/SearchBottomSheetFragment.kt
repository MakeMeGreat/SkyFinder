package com.example.skyfinder.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skyfinder.databinding.SearchBottomSheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: SearchBottomSheetFragmentBinding? = null
    private val binding get() = _binding!!


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
        binding.editText.requestFocus()
    }
}