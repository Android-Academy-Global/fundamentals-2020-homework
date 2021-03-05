package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.academy.fundamentals.homework.common.model.Failure
import com.android.academy.fundamentals.homework.common.model.Result
import com.android.academy.fundamentals.homework.common.model.Success
import com.android.academy.fundamentals.homework.domain.MovieRepository
import com.android.academy.fundamentals.homework.extensions.exhaustive
import com.android.academy.fundamentals.homework.model.Movie
import kotlinx.coroutines.launch

internal class MoviesListViewModelImpl(private val repository: MovieRepository) : MoviesListViewModel() {

    override val moviesStateOutput = MutableLiveData<MoviesListViewState>()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch { handleResult(repository.loadMovies()) }
    }

    private fun handleResult(result: Result<List<Movie>>) {
        when (result) {
            is Success -> moviesStateOutput.postValue(MoviesListViewState.MoviesLoaded(result.data))
            is Failure -> moviesStateOutput.postValue(MoviesListViewState.FailedToLoad(result.exception))
        }.exhaustive
    }
}
