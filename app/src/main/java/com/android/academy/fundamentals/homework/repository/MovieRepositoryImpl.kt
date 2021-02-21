package com.android.academy.fundamentals.homework.repository

import com.android.academy.fundamentals.homework.data.remote.RemoteDataSource
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails

class MovieRepositoryImpl(
    private val remoteDataResource: RemoteDataSource
): MovieRepository {

    override suspend fun loadMovies(): List<Movie> {
        return remoteDataResource.loadMovies()
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails {
        return remoteDataResource.loadMovie(movieId)
    }
}