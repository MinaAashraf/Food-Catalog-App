package com.example.foodcatalogapp.catalog.data.api

import com.example.catalog.core.common.ResultState
import com.example.foodcatalogapp.catalog.data.model.ProductCategoryDTO
import com.example.foodcatalogapp.catalog.data.model.ProductDTO

interface CatalogService {
    suspend fun getCategories(): ResultState<List<ProductCategoryDTO>>
    suspend fun getProducts(): ResultState<List<ProductDTO>>
}