package com.android.academy.fundamentals.homework.features.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.academy.fundamentals.homework.data.MovieRepository
import com.android.academy.fundamentals.homework.features.moviedetails.MovieDetailsViewState.MovieLoaded
import com.android.academy.fundamentals.homework.features.moviedetails.MovieDetailsViewState.NoMovie
import com.android.academy.fundamentals.homework.model.Movie
import kotlinx.coroutines.launch

internal class MovieDetailsViewModelImpl(private val repository: MovieRepository) : MovieDetailsViewModel() {

    override val stateOutput = MutableLiveData<MovieDetailsViewState>()

    fun loadDetails(movieId: Int) {
        viewModelScope.launch {
            val movie = repository.loadMovie(movieId)

            handleMovieLoadResult(movie)
        }
    }

    private fun handleMovieLoadResult(movie: Movie?) {
        if (movie != null) {
            stateOutput.postValue(MovieLoaded(movie))
        } else {
            stateOutput.postValue(NoMovie)
        }
    }
}
