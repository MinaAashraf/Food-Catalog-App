package com.example.catalog.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "product_category")
data class ProductCategoryEntity(
    @PrimaryKey val id: Int,
    val name: String? = null
)