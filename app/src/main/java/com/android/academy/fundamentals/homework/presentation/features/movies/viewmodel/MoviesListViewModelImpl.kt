package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.repository.MovieRepository
import kotlinx.coroutines.launch

internal class MoviesListViewModelImpl(private val repository: MovieRepository) : MoviesListViewModel() {

    override val moviesOutput = MutableLiveData<List<Movie>>()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            moviesOutput.postValue(repository.loadMovies())
        }
    }
}
