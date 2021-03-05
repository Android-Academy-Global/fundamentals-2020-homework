package com.android.academy.fundamentals.homework.data

import com.android.academy.fundamentals.homework.common.model.Result
import com.android.academy.fundamentals.homework.common.model.runCatchingResult
import com.android.academy.fundamentals.homework.data.remote.RemoteDataSource
import com.android.academy.fundamentals.homework.domain.MovieRepository
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails

internal class MovieRepositoryImpl(
    private val remoteDataResource: RemoteDataSource,
) : MovieRepository {

    override suspend fun loadMovies(): Result<List<Movie>> {
        return runCatchingResult { remoteDataResource.loadMovies() }
    }

    override suspend fun loadMovie(movieId: Int): Result<MovieDetails> {
        return runCatchingResult{remoteDataResource.loadMovie(movieId)}
    }
}
