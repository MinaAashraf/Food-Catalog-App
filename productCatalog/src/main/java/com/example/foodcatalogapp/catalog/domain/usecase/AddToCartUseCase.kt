package com.example.foodcatalogapp.catalog.domain.usecase

import com.example.foodcatalogapp.catalog.domain.model.CartDetailsModel
import com.example.foodcatalogapp.catalog.domain.repository.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AddToCartUseCase(
    private val repository: CartRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(cartDetails: CartDetailsModel) =
        withContext(dispatcher) {
            repository.addToCart(cartDetails)
        }
}