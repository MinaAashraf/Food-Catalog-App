package com.example.foodcatalogapp.catalog.presentation.mapper

import com.example.foodcatalogapp.catalog.domain.model.CatalogModel
import com.example.foodcatalogapp.catalog.domain.model.ProductModel
import com.example.foodcatalogapp.catalog.presentation.model.CatalogPresentationModel
import com.example.foodcatalogapp.catalog.presentation.model.ProductPresentationModel
import com.example.foodcatalogapp.catalog.presentation.utils.toTwoDigitDecimals

fun List<CatalogModel>.toCatalogPresentationModel() =
    map {
        CatalogPresentationModel(
            categoryId = it.categoryId ?: 0,
            categoryName = it.categoryName.orEmpty(),
            products = it.products?.toProductPresentationModels().orEmpty()
        )
    }

fun List<ProductModel>.toProductPresentationModels() =
    map {
        ProductPresentationModel(
            id = it.id ?: 0,
            name = it.name.orEmpty(),
            description = it.description,
            image = it.image.orEmpty(),
            price = it.price?.toTwoDigitDecimals().orEmpty()
        )
    }