package com.taskmanager.data.remote

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Error(val code: Int? = null, val message: String, val throwable: Throwable? = null) : NetworkResult<Nothing>
}
