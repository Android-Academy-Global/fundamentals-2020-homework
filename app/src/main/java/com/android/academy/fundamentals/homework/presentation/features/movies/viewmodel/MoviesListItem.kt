package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.common.text.NativeText
import com.android.academy.fundamentals.homework.model.Genre

data class MoviesListItem(
    val id: Int,
    val pgAge: Int,
    val title: String,
    val genres: List<Genre>,
    val runningTime: Int,
    val reviewCount: Int,
    val isLiked: Boolean,
    val rating: Int,
    val imageUrl: String?,
    val release: NativeText
)
