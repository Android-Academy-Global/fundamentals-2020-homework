package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

internal sealed class MoviesListViewState {

    data class MoviesLoaded(val movies: List<MoviesListItem>) : MoviesListViewState()

    object FailedToLoad : MoviesListViewState()
}
