package com.example.foodcatalogapp.catalog.presentation.state

import com.example.foodcatalogapp.catalog.presentation.model.CatalogPresentationModel

data class CatalogUiState(
    val catalogResult: CatalogResult = CatalogResult.Loading,
    val searchQuery: String? = null,
    val selectedTabIndex: Int = 0
)

sealed class CatalogResult {
    data class CatalogSuccess(val catalogs: List<CatalogPresentationModel>) : CatalogResult()
    data class Error (val message: Int) : CatalogResult()
    data object Loading : CatalogResult()
}
