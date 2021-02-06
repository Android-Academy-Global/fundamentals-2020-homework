package com.android.academy.fundamentals.homework.features.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.academy.fundamentals.homework.data.MovieRepository
import com.android.academy.fundamentals.homework.model.Movie
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movie = MutableLiveData<Movie?>()
    val movie: LiveData<Movie?> = _movie

    fun loadDetails(movieId: Int) {
        viewModelScope.launch {
            _movie.postValue(repository.loadMovie(movieId))
        }
    }
}