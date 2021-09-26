package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.academy.fundamentals.homework.R
import com.android.academy.fundamentals.homework.common.model.Failure
import com.android.academy.fundamentals.homework.common.model.Result
import com.android.academy.fundamentals.homework.common.model.Success
import com.android.academy.fundamentals.homework.common.text.NativeText
import com.android.academy.fundamentals.homework.common.time.CurrentTimeProvider
import com.android.academy.fundamentals.homework.domain.MovieRepository
import com.android.academy.fundamentals.homework.extensions.exhaustive
import com.android.academy.fundamentals.homework.model.Movie
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit

internal class MoviesListViewModelImpl(
    private val repository: MovieRepository,
    private val currentTimeProvider: CurrentTimeProvider,
) : MoviesListViewModel() {

    override val moviesStateOutput = MutableLiveData<MoviesListViewState>()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch { handleResult(repository.loadMovies()) }
    }

    private fun handleResult(result: Result<List<Movie>>) {
        when (result) {
            is Success -> moviesStateOutput.postValue(MoviesListViewState.MoviesLoaded(result.data.map { it.toListItem() }))
            is Failure -> moviesStateOutput.postValue(MoviesListViewState.FailedToLoad)
        }.exhaustive
    }

    private fun Movie.toListItem(): MoviesListItem {
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
            releaseAt = calculateDaysBeforeRelease(this.releaseDate),
        )
    }

    private fun calculateDaysBeforeRelease(releaseDate: LocalDate): NativeText {
        val currentDate = currentTimeProvider.getCurrentTime().toLocalDate()
        return when {
            releaseDate.isAfter(currentDate) -> {
                val daysBeforeRelease = daysBetween(currentDate, releaseDate)
                NativeText.Plural(
                    R.plurals.movies_list_days_before_release,
                    daysBeforeRelease,
                    listOf(daysBeforeRelease)
                )
            }
            releaseDate.isBefore(currentDate) -> {
                val daysAfterRelease = daysBetween(releaseDate, currentDate)
                NativeText.Plural(
                    R.plurals.movies_list_days_after_release,
                    daysAfterRelease,
                    listOf(daysAfterRelease)
                )
            }
            else -> NativeText.Resource(R.string.movies_list_released_today)
        }
    }

    private fun daysBetween(
        releaseDate: LocalDate,
        currentDate: LocalDate
    ) = ChronoUnit.DAYS.between(releaseDate, currentDate).toInt()
}
