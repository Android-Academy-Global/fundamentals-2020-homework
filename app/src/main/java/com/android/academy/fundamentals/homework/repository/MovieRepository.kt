package com.android.academy.fundamentals.homework.repository

import com.android.academy.fundamentals.homework.model.MovieDetails
import com.android.academy.fundamentals.homework.model.Movie

interface MovieRepository {
    suspend fun fetchConfiguration()
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): MovieDetails
}
