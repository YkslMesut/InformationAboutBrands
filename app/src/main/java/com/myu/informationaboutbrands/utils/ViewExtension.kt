package com.myu.informationaboutbrands.utils

import android.view.View
import androidx.core.content.ContextCompat
import com.myu.informationaboutbrands.R

fun View.addForegroundRipple() {
    foreground = ContextCompat.getDrawable(context, R.drawable.ripple_basic_shadow)
    isClickable = true
    isFocusable = true
}