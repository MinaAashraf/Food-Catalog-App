package com.example.catalog.core.cache.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catalog.core.cache.database.entity.ProductCategoryEntity
import com.example.catalog.core.cache.database.entity.ProductEntity

@Dao
interface CatalogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(categories: List<ProductCategoryEntity>)

    @Query("SELECT * FROM product_category")
    fun getCategories(): List<ProductCategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products")
    fun getProducts(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE name like :name")
    fun getProductByName(name: String): List<ProductEntity>
}