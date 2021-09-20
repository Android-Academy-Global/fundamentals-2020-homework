package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import android.content.Context
import androidx.annotation.StringRes

/**
 * @author y.anisimov
 */
interface ResourceProvider {
    fun getString(@StringRes id: Int): String
}

class ResourceProviderImpl(
    private val context: Context
) : ResourceProvider {
    override fun getString(id: Int): String {
        return context.getString(id)
    }
}
