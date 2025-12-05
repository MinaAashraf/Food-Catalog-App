package com.example.foodcatalogapp.catalog.presentation.utils

fun Double.toTwoDigitDecimals(): String {
    return String.format("%.2f", this)
}