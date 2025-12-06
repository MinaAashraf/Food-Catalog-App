package com.example.foodcatalogapp.catalog.data.mapper

import com.example.catalog.core.data.database.entity.CartDetailsEntity
import com.example.foodcatalogapp.catalog.domain.model.CartDetailsModel

fun CartDetailsModel.toCartDetailsEntity () =
    CartDetailsEntity(
        productIds = productIds,
        totalPrice = totalPrice
    )