package com.android.academy.fundamentals.homework.presentation.features.moviedetails.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.academy.fundamentals.homework.data.MovieRepository
import com.android.academy.fundamentals.homework.model.MovieDetails
import com.android.academy.fundamentals.homework.presentation.features.moviedetails.viewmodel.MovieDetailsViewState.FailedToLoad
import com.android.academy.fundamentals.homework.presentation.features.moviedetails.viewmodel.MovieDetailsViewState.MovieLoaded
import com.android.academy.fundamentals.homework.presentation.features.moviedetails.viewmodel.MovieDetailsViewState.NoMovie
import kotlinx.coroutines.launch
import java.io.IOException

internal class MovieDetailsViewModelImpl(private val repository: MovieRepository) : MovieDetailsViewModel() {

    override val stateOutput = MutableLiveData<MovieDetailsViewState>()

    fun loadDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val movie = repository.loadMovie(movieId)
                handleMovieLoadResult(movie)
            } catch (e: IOException) {
                stateOutput.postValue(FailedToLoad(e))
            }
        }
    }

    private fun handleMovieLoadResult(movie: MovieDetails?) {
        if (movie != null) {
            stateOutput.postValue(MovieLoaded(movie))
        } else {
            stateOutput.postValue(NoMovie)
        }
    }
}
