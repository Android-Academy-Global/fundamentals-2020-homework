package com.android.academy.fundamentals.homework.domain

import com.android.academy.fundamentals.homework.common.model.Result
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails

internal interface MovieRepository {

    suspend fun loadMovies(): Result<List<Movie>>
    suspend fun loadMovie(movieId: Int): Result<MovieDetails>
}
