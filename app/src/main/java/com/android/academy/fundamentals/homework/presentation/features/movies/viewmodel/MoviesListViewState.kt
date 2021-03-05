package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.model.Movie

internal sealed class MoviesListViewState {

    data class MoviesLoaded(val movies: List<Movie>) : MoviesListViewState()

    data class FailedToLoad(val exception: Throwable) : MoviesListViewState()
}
