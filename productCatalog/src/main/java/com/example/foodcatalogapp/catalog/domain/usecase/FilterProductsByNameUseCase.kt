package com.example.foodcatalogapp.catalog.domain.usecase

import com.example.catalog.core.common.mapFromResultState
import com.example.foodcatalogapp.catalog.domain.model.CatalogModel
import com.example.foodcatalogapp.catalog.domain.repository.CatalogRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FilterProductsByNameUseCase(
    private val repository: CatalogRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(productName: String) =
        withContext(dispatcher) {
            repository.filterProductsByName(productName).mapFromResultState { products ->
                listOf(
                    CatalogModel(
                        categoryName = productName,
                        products = products
                    )
                )
            }
        }
}