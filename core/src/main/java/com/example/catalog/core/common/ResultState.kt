package com.example.catalog.core.common

import kotlinx.serialization.Serializable

@Serializable
sealed class ResultState<out T : Any> {
    @Serializable
    data class Success<T : Any>(val body: T) : ResultState<T>()
    @Serializable
    data class Error<T : Any>(val error: ErrorCatalog) : ResultState<T>()
}

fun <T : Any, R : Any> ResultState<T>.mapFromResultState(
    transformer: (value: T) -> R
): ResultState<R> {
    return if (this is ResultState.Success<T>) {
        ResultState.Success(transformer(this.body))
    } else this as ResultState<R>
}

suspend fun <T : Any> ResultState<T>.onSuccess(
    dataResult: suspend (T) -> Unit
): ResultState<T> = apply {
    if (this is ResultState.Success<T>) {
        dataResult(this.body)
    }
}

suspend fun <T : Any> ResultState<T>.onError(
    dataResult: suspend (error: ErrorCatalog) -> Unit
): ResultState<T> = apply {
    if (this is ResultState.Error<T>) {
        dataResult(error)
    }
}