package com.example.tastebuds

sealed class ServiceResponse<out T: Any> {
    data class Success<out T : Any>(val data: T) : ServiceResponse<T>()
    data class Error(val exception: String) : ServiceResponse<Nothing>()
}

