package com.android.academy.fundamentals.homework.common.model

internal sealed class Result<out T>

internal data class Success<T>(val data: T) : Result<T>()

internal data class Failure(val exception: Throwable) : Result<Nothing>()

internal inline fun <R> runCatchingResult(block: () -> R): Result<R> {
    return try {
        Success(block())
    } catch (e: Throwable) {
        Failure(e)
    }
}
