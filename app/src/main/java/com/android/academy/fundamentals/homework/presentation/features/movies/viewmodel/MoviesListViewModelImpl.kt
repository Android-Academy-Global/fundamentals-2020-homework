package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.academy.fundamentals.homework.data.MovieRepository
import kotlinx.coroutines.launch
import java.io.IOException

internal class MoviesListViewModelImpl(private val repository: MovieRepository) : MoviesListViewModel() {

    override val moviesStateOutput = MutableLiveData<MoviesListViewState>()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            try {
                moviesStateOutput.postValue(MoviesListViewState.MoviesLoaded(repository.loadMovies()))
            } catch (e: IOException) {
                moviesStateOutput.postValue(MoviesListViewState.FailedToLoad(e))
            }
        }
    }
}
