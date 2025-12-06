package com.example.foodcatalogapp.catalog.data.mapper

import com.example.catalog.core.data.database.entity.ProductCategoryEntity
import com.example.catalog.core.data.database.entity.ProductEntity
import com.example.foodcatalogapp.catalog.data.model.ProductCategoryDTO
import com.example.foodcatalogapp.catalog.data.model.ProductDTO
import com.example.foodcatalogapp.catalog.domain.model.ProductCategoryModel
import com.example.foodcatalogapp.catalog.domain.model.ProductModel

fun List<ProductCategoryDTO>.toCategoryEntities() =
    filter { it.id != null }
        .map {
            ProductCategoryEntity(
                id = it.id ?: 0,
                name = it.name
            )
        }

fun List<ProductDTO>.toProductEntities() =
    filter { it.id != null && it.categoryId != null }
        .map {
            ProductEntity(
                id = it.id ?: 0,
                name = it.name,
                description = it.description,
                image = it.image,
                price = it.price,
                categoryId = it.categoryId ?: 0
            )
        }

fun List<ProductCategoryEntity>.toCategoryDomainModels() =
    map {
        ProductCategoryModel(
            id = it.id,
            name = it.name
        )
    }

fun List<ProductEntity>.toProductDomainModels() =
    map {
        ProductModel(
            id = it.id,
            name = it.name,
            description = it.description,
            image = it.image,
            price = it.price,
            categoryId = it.categoryId
        )
    }