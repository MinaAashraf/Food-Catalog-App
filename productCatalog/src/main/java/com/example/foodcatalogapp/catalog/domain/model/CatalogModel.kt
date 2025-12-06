package com.example.foodcatalogapp.catalog.domain.model

data class CatalogModel(
    val categoryId: Int? = null,
    val categoryName: String? = null,
    val products: List<ProductModel>? = null
)

data class ProductCategoryModel(
    val id: Int,
    val name: String? = null
)

data class ProductModel(
    val id: Int,
    val name: String? = null,
    val description: String? = null,
    val image: String? = null,
    val price: Double? = null,
    val categoryId: Int
)