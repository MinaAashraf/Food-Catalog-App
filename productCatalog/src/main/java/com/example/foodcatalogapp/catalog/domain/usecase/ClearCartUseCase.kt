package com.example.foodcatalogapp.catalog.domain.usecase

import com.example.foodcatalogapp.catalog.domain.repository.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ClearCartUseCase(
    private val repository: CartRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() =
        withContext(dispatcher) {
            repository.clearCart()
        }
}