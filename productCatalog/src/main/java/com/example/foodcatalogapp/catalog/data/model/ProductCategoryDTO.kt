package com.example.foodcatalogapp.catalog.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductCategoryDTO(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null
)