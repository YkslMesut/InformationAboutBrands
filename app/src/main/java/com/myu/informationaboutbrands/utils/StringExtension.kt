package com.myu.informationaboutbrands.utils

import com.google.gson.GsonBuilder

inline fun <reified T> String.fromJsonToList(): List<T> {
    val gson = GsonBuilder().create()
    return gson.fromJson(this, Array<T>::class.java).toList()
}