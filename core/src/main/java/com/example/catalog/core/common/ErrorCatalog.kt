package com.example.catalog.core.common

import kotlinx.serialization.Serializable

@Serializable
sealed class ErrorCatalog {
    data object NetworkError : ErrorCatalog()
    data object DatabaseError : ErrorCatalog()
    data object NoDataFoundError : ErrorCatalog()
}