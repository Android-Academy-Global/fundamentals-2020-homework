package com.android.academy.fundamentals.homework.data.locale

import com.android.academy.fundamentals.homework.model.MovieOld

interface LocalDataSource {
    suspend fun loadMovies(): List<MovieOld>
    suspend fun loadMovie(movieId: Int): MovieOld?
}