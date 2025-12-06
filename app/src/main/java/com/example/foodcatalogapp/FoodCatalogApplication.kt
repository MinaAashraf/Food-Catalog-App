package com.example.foodcatalogapp

import android.app.Application
import com.example.catalog.core.data.database.di.databaseModule
import com.example.catalog.core.data.network.di.networkModule
import com.example.foodcatalogapp.catalog.di.catalogDataModule
import com.example.foodcatalogapp.catalog.di.catalogDomainModule
import com.example.foodcatalogapp.catalog.di.catalogPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class FoodCatalogApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FoodCatalogApplication)
            androidLogger(Level.DEBUG)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    catalogDataModule,
                    catalogDomainModule,
                    catalogPresentationModule
                )
            )
        }
    }
}