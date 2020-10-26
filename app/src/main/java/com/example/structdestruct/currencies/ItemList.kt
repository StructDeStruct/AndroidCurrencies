package com.example.structdestruct.currencies

data class ItemList(
    val dateStock: Long,
    val value: Float,
    val fromCurrency: String,
    val toCurrency: String,
    val minOfDay: Float,
    val maxOfDay: Float
)