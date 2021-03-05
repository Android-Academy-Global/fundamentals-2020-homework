package com.android.academy.fundamentals.homework.presentation.features.moviedetails.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.academy.fundamentals.homework.common.model.Failure
import com.android.academy.fundamentals.homework.common.model.Result
import com.android.academy.fundamentals.homework.common.model.Success
import com.android.academy.fundamentals.homework.domain.MovieRepository
import com.android.academy.fundamentals.homework.extensions.exhaustive
import com.android.academy.fundamentals.homework.model.MovieDetails
import com.android.academy.fundamentals.homework.presentation.features.moviedetails.viewmodel.MovieDetailsViewState.FailedToLoad
import com.android.academy.fundamentals.homework.presentation.features.moviedetails.viewmodel.MovieDetailsViewState.MovieLoaded
import com.android.academy.fundamentals.homework.presentation.features.moviedetails.viewmodel.MovieDetailsViewState.NoMovie
import kotlinx.coroutines.launch

internal class MovieDetailsViewModelImpl(private val repository: MovieRepository) : MovieDetailsViewModel() {

    override val stateOutput = MutableLiveData<MovieDetailsViewState>()

    fun loadDetails(movieId: Int) {
        viewModelScope.launch {
            val movie = repository.loadMovie(movieId)

            handleResult(movie)
        }
    }

    private fun handleResult(result: Result<MovieDetails?>) {
        when (result) {
            is Success -> handleMovieLoadResult(result.data)
            is Failure -> stateOutput.postValue(FailedToLoad(result.exception))
        }.exhaustive
    }

    private fun handleMovieLoadResult(movie: MovieDetails?) {
        if (movie != null) {
            stateOutput.postValue(MovieLoaded(movie))
        } else {
            stateOutput.postValue(NoMovie)
        }
    }
}
