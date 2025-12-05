package com.example.foodcatalogapp

import android.app.Application
import com.example.catalog.core.cache.database.di.databaseModule
import com.example.catalog.core.network.networkModule
import com.example.foodcatalogapp.catalog.di.catalogDataModule
import com.example.foodcatalogapp.catalog.di.catalogDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class FoodCatalogApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FoodCatalogApplication)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    catalogDataModule,
                    catalogDomainModule
                )
            )
        }
    }
}