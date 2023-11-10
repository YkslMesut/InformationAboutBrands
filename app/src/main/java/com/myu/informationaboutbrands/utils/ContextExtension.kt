package com.myu.informationaboutbrands.utils

import android.content.Context

fun Context.readFromFile(fileName: String): String {
    return assets.open(fileName).bufferedReader().use { it.readText() }
}