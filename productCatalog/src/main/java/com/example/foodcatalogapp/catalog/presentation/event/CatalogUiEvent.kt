package com.example.foodcatalogapp.catalog.presentation.event

sealed class CatalogUiEvent {
    data class OnSearchQuery(val query: String) : CatalogUiEvent()
    data class OnCategoryTabClick(val tabIndex: Int) : CatalogUiEvent()
    data class OnProductClick(val productId: Int, val productPrice: String) : CatalogUiEvent()
}