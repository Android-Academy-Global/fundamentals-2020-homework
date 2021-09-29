package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.R
import com.android.academy.fundamentals.homework.common.text.NativeText
import com.android.academy.fundamentals.homework.model.Movie
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit

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
            release = when {
                movie.releaseDate.isBefore(LocalDate.now()) -> {
                    val period = ChronoUnit.DAYS.between(movie.releaseDate, LocalDate.now()).toInt()
                    NativeText.Plural(R.plurals.movies_list_days_before_release, period, listOf(period))
                }
                else -> NativeText.Resource(R.string.movies_list_released_today)
            })
    }
}