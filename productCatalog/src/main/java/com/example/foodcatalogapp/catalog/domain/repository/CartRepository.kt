package com.example.foodcatalogapp.catalog.domain.repository

import com.example.catalog.core.common.ResultState
import com.example.foodcatalogapp.catalog.domain.model.CartDetailsModel
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun readCartDetails(): Flow<CartDetailsModel>
    suspend fun addToCart(cartDetails: CartDetailsModel)
    suspend fun clearCart()
}