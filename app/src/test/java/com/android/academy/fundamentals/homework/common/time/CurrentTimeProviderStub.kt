package com.android.academy.fundamentals.homework.common.time

import java.time.LocalDateTime

class CurrentTimeProviderStub(
    private val currentTime: LocalDateTime
) : CurrentTimeProvider {
    override fun getCurrentTime(): LocalDateTime {
        return currentTime
    }
}