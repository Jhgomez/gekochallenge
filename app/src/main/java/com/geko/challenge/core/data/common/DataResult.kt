package com.geko.challenge.core.data.common

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Failed(val message: String) : DataResult<Nothing>()
    object UnknownFailure : DataResult<Nothing>()
}