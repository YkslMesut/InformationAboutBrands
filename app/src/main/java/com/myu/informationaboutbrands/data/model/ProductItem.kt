package com.myu.informationaboutbrands.data.model

data class ProductItem(
    val category: List<String>,
    val description: String,
    val howToBoycott: List<String>,
    val id: String,
    val logo: String,
    val name: String,
    val reason: String,
    val source: String,
    val uuid: String
)