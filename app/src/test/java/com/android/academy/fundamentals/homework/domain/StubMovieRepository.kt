package com.android.academy.fundamentals.homework.domain

import com.android.academy.fundamentals.homework.common.model.Result
import com.android.academy.fundamentals.homework.common.model.Success
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails

internal class StubMovieRepository(
    private val movies: List<Movie> = emptyList()
) : MovieRepository {
    override suspend fun loadMovies(): Result<List<Movie>> = Success(movies)
    override suspend fun loadMovie(movieId: Int): Result<MovieDetails> =
        TODO("Stub repository doesn't implement loadMovie method")
}