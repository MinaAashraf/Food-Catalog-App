package com.example.foodcatalogapp.catalog.di

import com.example.foodcatalogapp.catalog.data.api.CatalogService
import com.example.foodcatalogapp.catalog.data.api.CatalogServiceImpl
import com.example.foodcatalogapp.catalog.data.api.Categories
import com.example.foodcatalogapp.catalog.data.api.Products
import com.example.foodcatalogapp.catalog.data.cache.CartDetailsDataStore
import com.example.foodcatalogapp.catalog.data.cache.CartDetailsDataStoreImpl
import com.example.foodcatalogapp.catalog.data.repository.CartRepositoryImpl
import com.example.foodcatalogapp.catalog.data.repository.CatalogRepositoryImpl
import com.example.foodcatalogapp.catalog.domain.repository.CartRepository
import com.example.foodcatalogapp.catalog.domain.repository.CatalogRepository
import com.example.foodcatalogapp.catalog.domain.usecase.AddToCartUseCase
import com.example.foodcatalogapp.catalog.domain.usecase.ClearCartUseCase
import com.example.foodcatalogapp.catalog.domain.usecase.FilterProductsByNameUseCase
import com.example.foodcatalogapp.catalog.domain.usecase.GetCatalogsUseCase
import com.example.foodcatalogapp.catalog.domain.usecase.ReadCartDetailsUseCase
import com.example.foodcatalogapp.catalog.presentation.CatalogViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
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
    singleOf(::CatalogRepositoryImpl) { bind<CatalogRepository>() }
    singleOf(::CartRepositoryImpl) { bind<CartRepository>() }
    singleOf(::CartDetailsDataStoreImpl) { bind<CartDetailsDataStore>() }
}

val catalogDomainModule = module {
    factoryOf(::GetCatalogsUseCase)
    factoryOf(::FilterProductsByNameUseCase)
    factoryOf(::ReadCartDetailsUseCase)
    factoryOf(::AddToCartUseCase)
    factoryOf(::ClearCartUseCase)
    factory { Dispatchers.IO }
}

val catalogPresentationModule = module {
    viewModelOf(::CatalogViewModel)
}