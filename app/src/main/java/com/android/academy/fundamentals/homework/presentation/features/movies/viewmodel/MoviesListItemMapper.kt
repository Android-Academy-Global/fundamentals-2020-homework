package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.model.Movie

class MoviesListItemMapper {
    fun map(movie: Movie): MoviesListItem {
        return MoviesListItem(
            id = movie.id,
            pgAge = movie.pgAge,
            title = movie.title,
            genres = movie.genres,
            runningTime = movie.runningTime,
            reviewCount = movie.reviewCount,
            isLiked = movie.isLiked,
            rating = movie.rating,
            imageUrl = movie.imageUrl,
        )
    }
}