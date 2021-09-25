package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.model.Movie

/**
 * @author y.anisimov
 */

fun Movie.toListItem(): MoviesListItem {
    return MoviesListItem(
        id = this.id,
        pgAge = this.pgAge,
        title = this.title,
        genres = this.genres,
        runningTime = this.runningTime,
        reviewCount = this.reviewCount,
        isLiked = this.isLiked,
        rating = this.rating,
        imageUrl = this.imageUrl,
    )
}
