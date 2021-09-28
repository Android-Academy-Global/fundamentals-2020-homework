package com.android.academy.fundamentals.homework.data.remote.retrofit

import com.android.academy.fundamentals.homework.data.remote.retrofit.response.MovieResponse

fun createMovieResponse(
    id: Int = 0,
    title: String = "",
    posterPath: String = "",
    adult: Boolean = false,
    genreIds: List<Int> = emptyList(),
    voteCount: Int = 0,
    voteAverage: Double = 0.0,
    releaseDate: String = ""
): MovieResponse = MovieResponse(
    id = id,
    title = title,
    posterPath = posterPath,
    adult = adult,
    genreIds = genreIds,
    voteCount = voteCount,
    voteAverage = voteAverage,
    releaseDate = releaseDate
)
