package com.example.foodcatalogapp.catalog.domain.repository

import com.example.catalog.core.common.ResultState
import com.example.foodcatalogapp.catalog.domain.model.ProductCategoryModel
import com.example.foodcatalogapp.catalog.domain.model.ProductModel

interface CatalogRepository {
    suspend fun getCategories(): ResultState<List<ProductCategoryModel>>
    suspend fun getProducts(): ResultState<List<ProductModel>>
    suspend fun filterProductsByName(productName: String): ResultState<List<ProductModel>>
}