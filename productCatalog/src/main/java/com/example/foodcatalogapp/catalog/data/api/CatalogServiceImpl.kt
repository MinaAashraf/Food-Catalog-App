package com.example.foodcatalogapp.catalog.data.api

import com.example.catalog.core.common.ErrorCatalog
import com.example.catalog.core.common.ResultState
import com.example.foodcatalogapp.catalog.data.model.ProductCategoryDTO
import com.example.foodcatalogapp.catalog.data.model.ProductDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource

class CatalogServiceImpl(
    private val client: HttpClient,
    private val categoriesResource: Categories,
    private val productsResource: Products
) : CatalogService {
    override suspend fun getCategories(): ResultState<List<ProductCategoryDTO>> {
        return try {
            client.get(categoriesResource).body()
        } catch (_: Exception) {
            ResultState.Error(ErrorCatalog.NetworkError)
        }
    }

    override suspend fun getProducts(): ResultState<List<ProductDTO>> {
        return try {
            client.get(productsResource).body()
        } catch (_: Exception) {
            ResultState.Error(ErrorCatalog.NetworkError)
        }
    }
}

@Resource("/Categories")
class Categories

@Resource("/Products")
class Products