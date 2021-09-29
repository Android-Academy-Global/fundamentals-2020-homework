package com.android.academy.fundamentals.homework.model

import java.time.LocalDate

fun createMovie(
    id: Int = 0,
    title: String = "",
    reviewCount: Int = 0,
    isLiked: Boolean = false,
    rating: Int = 0,
    pgAge: Int = 0,
    genres: List<Genre> = emptyList(),
    runningTime: Int = 0,
    imageUrl: String? = null,
    releaseDate: LocalDate = LocalDate.of(2021, 3, 8)
): Movie {
    return Movie(
        id = id,
        title = title,
        reviewCount = reviewCount,
        isLiked = isLiked,
        rating = rating,
        pgAge = pgAge,
        genres = genres,
        runningTime = runningTime,
        imageUrl = imageUrl,
        releaseDate = releaseDate
    )
}