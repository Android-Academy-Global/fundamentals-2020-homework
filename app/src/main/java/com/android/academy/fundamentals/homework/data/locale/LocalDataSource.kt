package com.android.academy.fundamentals.homework.data.locale

import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails

interface LocalDataSource {
    suspend fun loadMovies(): List<Movie>
    fun insertMovies(movieFromNetwork: List<Movie>)
    suspend fun loadMovie(movieId: Int): MovieDetails
    fun insertMovieDetails(movieDetailsFromNetwork: Movie)
}