package com.example.catalog.core.cache.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "product_category")
data class ProductCategoryEntity(
    @PrimaryKey val id: Int? = null,
    val name: String? = null
)