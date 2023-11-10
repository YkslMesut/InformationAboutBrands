package com.myu.informationaboutbrands.data.repository

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val code: Int?,
        val message: String?
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()

    object Empty : Resource<Nothing>()
}