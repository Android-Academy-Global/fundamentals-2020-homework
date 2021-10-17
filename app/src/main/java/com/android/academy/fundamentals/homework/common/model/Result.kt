package com.android.academy.fundamentals.homework.common.model

sealed class Result<out T>

data class Success<T>(val data: T) : Result<T>()

data class Failure(val exception: Throwable) : Result<Nothing>()

inline fun <R> runCatchingResult(block: () -> R): Result<R> {
    return try {
        Success(block())
    } catch (e: Throwable) {
        Failure(e)
    }
}
