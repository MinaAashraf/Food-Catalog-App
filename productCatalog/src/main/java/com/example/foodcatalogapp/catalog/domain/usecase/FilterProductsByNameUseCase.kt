package com.example.foodcatalogapp.catalog.domain.usecase

import com.example.foodcatalogapp.catalog.domain.repository.CatalogRepository

class FilterProductsByNameUseCase(private val repository: CatalogRepository) {
    suspend operator fun invoke(productName: String) =
        repository.filterProductsByName(productName)
}