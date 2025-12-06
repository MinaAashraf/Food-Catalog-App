package com.example.foodcatalogapp.catalog.domain.model

data class CartDetailsModel(
    val productIds: List<Int> = emptyList(),
    val totalPrice: String = "0.0"
)