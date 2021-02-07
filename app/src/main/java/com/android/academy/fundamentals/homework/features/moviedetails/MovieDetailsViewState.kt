package com.android.academy.fundamentals.homework.features.moviedetails

import com.android.academy.fundamentals.homework.model.Movie

internal sealed class MovieDetailsViewState {

    data class MovieLoaded(val movie: Movie) : MovieDetailsViewState()

    object NoMovie : MovieDetailsViewState()
}
