package com.example.catalog.core.cache.database.di

import androidx.room.Room
import com.example.catalog.core.cache.database.CatalogDatabase
import com.example.catalog.core.cache.database.DATABASE_NAME
import com.example.catalog.core.cache.database.dao.CatalogDAO
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single<CatalogDAO> {
        Room.databaseBuilder(
            androidContext(),
            CatalogDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
            .CatalogDAO()
    }
}