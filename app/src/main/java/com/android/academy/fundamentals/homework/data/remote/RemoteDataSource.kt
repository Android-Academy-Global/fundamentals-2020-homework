package com.android.academy.fundamentals.homework.data.remote

import com.android.academy.fundamentals.homework.model.MovieDetails
import com.android.academy.fundamentals.homework.model.Movie

interface RemoteDataSource {

    suspend fun fetchConfiguration()
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): MovieDetails
}