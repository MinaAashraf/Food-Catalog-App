package com.example.catalog.core.common

sealed class ErrorCatalog {
    data object NetworkError : ErrorCatalog()
    data object NoDataFoundError : ErrorCatalog()
}