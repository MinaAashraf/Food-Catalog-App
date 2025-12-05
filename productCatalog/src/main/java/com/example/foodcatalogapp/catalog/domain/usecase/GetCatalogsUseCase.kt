package com.example.foodcatalogapp.catalog.domain.usecase

import com.example.catalog.core.common.ErrorCatalog
import com.example.catalog.core.common.ResultState
import com.example.catalog.core.common.mapFromResultState
import com.example.foodcatalogapp.catalog.domain.model.CatalogModel
import com.example.foodcatalogapp.catalog.domain.repository.CatalogRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetCatalogsUseCase(
    private val repository: CatalogRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): ResultState<List<CatalogModel>> {
        return withContext(dispatcher) {
            val categoriesDeferred = async { repository.getCategories() }
            val productsDeferred = async { repository.getProducts() }

            val categoriesResult = categoriesDeferred.await()
            val productsResult = productsDeferred.await()

            if (categoriesResult is ResultState.Success && productsResult is ResultState.Success) {
                categoriesResult.mapFromResultState { categories ->
                    val productsByCategory = productsResult.body.groupBy { it.categoryId }
                    categories.mapNotNull { category ->
                        productsByCategory[category.id]
                            ?.let { categoryProducts ->
                                CatalogModel(
                                    categoryId = category.id,
                                    categoryName = category.name,
                                    products = categoryProducts
                                )
                            }
                    }
                }
            } else {
                ResultState.Error(ErrorCatalog.NoDataFoundError)
            }
        }
    }
}