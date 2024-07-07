package com.example.skyfinder.presentation.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MainOfferItemDecorator(
    private val spaceBetween: Int,
    private val spaceSide: Int
    ): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
//        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = spaceSide
        } else {
            outRect.left = spaceBetween
            if (parent.getChildAdapterPosition(view) == parent.childCount - 1) {
                outRect.right = spaceSide
            }
        }
    }
}