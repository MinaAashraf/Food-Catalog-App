package com.example.catalog.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.catalog.core.data.database.dao.CatalogDAO
import com.example.catalog.core.data.database.entity.ProductCategoryEntity
import com.example.catalog.core.data.database.entity.ProductEntity

@Database(
    entities = [ProductCategoryEntity::class, ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CatalogDatabase : RoomDatabase() {
    abstract fun CatalogDAO(): CatalogDAO
}

const val DATABASE_NAME = "catalog_database"