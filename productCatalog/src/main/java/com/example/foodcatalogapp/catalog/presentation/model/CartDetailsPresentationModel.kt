package com.example.foodcatalogapp.catalog.presentation.model

import androidx.compose.runtime.Stable

@Stable
data class CartDetailsPresentationModel(
    val productIds: List<Int> = emptyList(),
    val totalPrice: String = "0"
)