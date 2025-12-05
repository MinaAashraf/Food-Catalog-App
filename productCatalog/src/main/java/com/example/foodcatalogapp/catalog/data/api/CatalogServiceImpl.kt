package com.example.foodcatalogapp.catalog.data.api

import android.util.Log
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
            Log.d("catalogRequest:", (client.get(categoriesResource).body() as List<ProductCategoryDTO>).toString())
            ResultState.Success(client.get(categoriesResource).body())
        } catch (e: Exception) {
            Log.d("catalogRequest:", e.toString())
            ResultState.Error(ErrorCatalog.NetworkError)
        }
    }

    override suspend fun getProducts(): ResultState<List<ProductDTO>> {
        return try {
            Log.d("productRequest:", (client.get(categoriesResource).body() as List<ProductCategoryDTO>).toString())

            ResultState.Success(client.get(productsResource).body())
        } catch (e: Exception) {
            Log.d("productRequest:", e.toString())
            ResultState.Error(ErrorCatalog.NetworkError)
        }
    }
}

@Resource("/categories.json")
class Categories()

@Resource("/products.json")
class Products