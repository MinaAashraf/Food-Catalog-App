package com.example.foodcatalogapp.catalog.presentation.model

data class CatalogPresentationModel(
    val categoryId: Int,
    val categoryName: String,
    val products: List<ProductPresentationModel>
)

data class ProductPresentationModel(
    val id: Int = 0,
    val name: String,
    val description: String? = null,
    val image: String,
    val price: String
)