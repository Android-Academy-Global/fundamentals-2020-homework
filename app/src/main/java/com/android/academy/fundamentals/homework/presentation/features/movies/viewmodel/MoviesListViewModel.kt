package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

internal abstract class MoviesListViewModel : ViewModel() {

    abstract val moviesStateOutput: LiveData<MoviesListViewState>
}
