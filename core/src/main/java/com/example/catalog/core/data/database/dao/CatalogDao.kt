package com.example.catalog.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catalog.core.data.database.entity.ProductCategoryEntity
import com.example.catalog.core.data.database.entity.ProductEntity

@Dao
interface CatalogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<ProductCategoryEntity>)

    @Query("SELECT * FROM product_category")
    suspend fun getCategories(): List<ProductCategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products")
    suspend fun getProducts(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE lower(name) like lower(:name)")
    suspend fun getProductByName(name: String): List<ProductEntity>
}