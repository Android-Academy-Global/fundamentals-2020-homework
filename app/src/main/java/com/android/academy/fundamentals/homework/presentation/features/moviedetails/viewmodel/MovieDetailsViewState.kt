package com.android.academy.fundamentals.homework.presentation.features.moviedetails.viewmodel

import com.android.academy.fundamentals.homework.model.MovieDetails

internal sealed class MovieDetailsViewState {

    data class MovieLoaded(val movie: MovieDetails) : MovieDetailsViewState()

    object NoMovie : MovieDetailsViewState()
}
