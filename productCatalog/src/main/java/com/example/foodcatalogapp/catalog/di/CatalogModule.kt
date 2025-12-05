package com.example.foodcatalogapp.catalog.di

import com.example.foodcatalogapp.catalog.data.api.CatalogService
import com.example.foodcatalogapp.catalog.data.api.CatalogServiceImpl
import com.example.foodcatalogapp.catalog.data.api.Categories
import com.example.foodcatalogapp.catalog.data.api.Products
import com.example.foodcatalogapp.catalog.domain.usecase.GetCatalogUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val catalogDataModule = module {
    single<CatalogService> {
        CatalogServiceImpl(
            client = get(),
            categoriesResource = get(),
            productsResource = get()
        )
    }
    singleOf(::Categories)
    singleOf(::Products)
}

val catalogDomainModule = module {
    factoryOf(::GetCatalogUseCase)
    factory { Dispatchers.IO }
}