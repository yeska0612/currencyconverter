package com.example.currencyconverter

data class Currency(
    val name: String,
    val code: String,
    val rate: Double,
    val flagResId: Int
)