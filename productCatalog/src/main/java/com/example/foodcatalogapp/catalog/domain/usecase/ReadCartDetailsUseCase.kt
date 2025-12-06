package com.example.foodcatalogapp.catalog.domain.usecase

import com.example.foodcatalogapp.catalog.domain.repository.CartRepository

class ReadCartDetailsUseCase(
    private val repository: CartRepository
) {
    operator fun invoke() = repository.readCartDetails()
}