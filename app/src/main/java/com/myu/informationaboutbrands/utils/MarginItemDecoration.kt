package com.myu.informationaboutbrands.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val marginHorizontal: Int = 0,
    private val marginVertical: Int = 0
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = marginVertical
            }
            left = marginHorizontal
            right = marginHorizontal
            bottom = marginVertical
        }
    }
}