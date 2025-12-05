package com.example.catalog.core.cache.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val image: String? = null,
    val price: Double? = null,
    val categoryId: Int? = null
)