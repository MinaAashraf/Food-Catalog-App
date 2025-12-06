package com.example.catalog.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "cart_table")
data class CartDetailsEntity(
    val productIds: List<Int>? = null,
    val totalPrice: String = "0"
) {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    val id: Int = 0
}