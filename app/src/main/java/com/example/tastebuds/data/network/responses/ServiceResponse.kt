package com.example.tastebuds.data.network.responses

sealed class ServiceResponse<out T: Any> {
    data class Success<out T : Any>(val data: T) : ServiceResponse<T>()
    data class Error(val exception: String) : ServiceResponse<Nothing>()
}

