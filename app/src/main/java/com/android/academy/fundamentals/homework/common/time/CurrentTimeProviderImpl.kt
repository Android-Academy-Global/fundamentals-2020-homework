package com.android.academy.fundamentals.homework.common.time

import java.time.LocalDateTime

class CurrentTimeProviderImpl : CurrentTimeProvider {
    override fun getCurrentTime(): LocalDateTime {
        return LocalDateTime.now()
    }
}