package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.academy.fundamentals.homework.model.Movie

internal abstract class MoviesListViewModel : ViewModel() {

    abstract val moviesOutput: LiveData<List<Movie>>
}
