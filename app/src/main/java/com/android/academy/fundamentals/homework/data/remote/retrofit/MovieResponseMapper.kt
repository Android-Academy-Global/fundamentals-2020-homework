package com.android.academy.fundamentals.homework.data.remote.retrofit

import com.android.academy.fundamentals.homework.data.remote.retrofit.response.MovieResponse
import com.android.academy.fundamentals.homework.model.Movie

private const val ADULT_AGE = 16
private const val CHILD_AGE = 13

fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        imageUrl = null,
        rating = this.voteAverage.toInt(),
        reviewCount = this.voteCount,
        pgAge = if (this.adult) ADULT_AGE else CHILD_AGE,
        runningTime = 100,
        isLiked = false,
        genres = emptyList(),
        releaseDate = TODO()
    )
}
