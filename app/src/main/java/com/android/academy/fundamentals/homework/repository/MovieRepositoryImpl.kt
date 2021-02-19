package com.android.academy.fundamentals.homework.repository

import com.android.academy.fundamentals.homework.data.locale.LocalDataSource
import com.android.academy.fundamentals.homework.data.remote.RemoteDataSource
import com.android.academy.fundamentals.homework.model.MovieDetails
import com.android.academy.fundamentals.homework.model.Movie

class MovieRepositoryImpl(
    private val localDataResource: LocalDataSource,
    private val remoteDataResource: RemoteDataSource
): MovieRepository {

    override suspend fun fetchConfiguration() {
        remoteDataResource.fetchConfiguration()
    }

    override suspend fun loadMovies(): List<Movie> {
        return remoteDataResource.loadMovies()
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails {
        return remoteDataResource.loadMovie(movieId)
    }
}