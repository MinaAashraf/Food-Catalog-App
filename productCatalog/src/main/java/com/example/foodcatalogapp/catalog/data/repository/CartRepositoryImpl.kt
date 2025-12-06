package com.example.foodcatalogapp.catalog.data.repository

import com.example.foodcatalogapp.catalog.data.cache.CartDetailsDataStore
import com.example.foodcatalogapp.catalog.domain.model.CartDetailsModel
import com.example.foodcatalogapp.catalog.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CartRepositoryImpl(
    private val dataStore: CartDetailsDataStore
) : CartRepository {

    override fun readCartDetails(): Flow<CartDetailsModel> {
        return try {
            dataStore.getCartDetails()
        } catch (_: Exception) {
            flowOf(CartDetailsModel())
        }
    }

    override suspend fun addToCart(cartDetails: CartDetailsModel) =
        dataStore.saveCartDetails(cartDetails)


    override suspend fun clearCart() =
        dataStore.clearCartDetails()
}