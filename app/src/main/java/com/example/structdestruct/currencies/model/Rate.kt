package com.example.structdestruct.currencies.model

data class ChildDataClass (
    val time: Long? = null,
    val high: Float? = null,
    val low: Float? = null,
    val open: Float? = null,
    val volumefrom: Float? = null,
    val volumeto: Float? = null,
    val close: Float? = null,
    val conversionType: String? = null,
    val conversionSymbol: String? = null
)

data class ParentDataClass (
    val Aggregated: Boolean? = null,
    val TimeFrom: Long? = null,
    val TimeTo: Long? = null,
    val Data: List<ChildDataClass>? = null
)

data class Rate (
    val Response: String? = null,
    val Message: String? = null,
    val HasWarning: Boolean? = null,
    val Type: Int? = null,
    val Data: ParentDataClass? = null
)