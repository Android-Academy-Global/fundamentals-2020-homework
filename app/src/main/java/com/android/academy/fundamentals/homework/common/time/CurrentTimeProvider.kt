package com.android.academy.fundamentals.homework.common.time

import java.time.LocalDateTime

interface CurrentTimeProvider {
    fun getCurrentTime(): LocalDateTime
}