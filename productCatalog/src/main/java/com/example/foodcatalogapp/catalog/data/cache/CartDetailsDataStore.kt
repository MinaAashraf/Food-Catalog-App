package com.example.foodcatalogapp.catalog.data.cache

import com.example.foodcatalogapp.catalog.domain.model.CartDetailsModel
import kotlinx.coroutines.flow.Flow

interface CartDetailsDataStore {
    suspend fun saveCartDetails(cartDetails: CartDetailsModel)

    fun getCartDetails () : Flow<CartDetailsModel>
    suspend fun clearCartDetails()
}