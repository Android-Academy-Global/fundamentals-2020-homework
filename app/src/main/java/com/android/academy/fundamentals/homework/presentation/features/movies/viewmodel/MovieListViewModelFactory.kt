package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.academy.fundamentals.homework.common.time.CurrentTimeProviderImpl
import com.android.academy.fundamentals.homework.domain.MovieRepository

@Suppress("UNCHECKED_CAST")
internal class MovieListViewModelFactory(private val repository: MovieRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MoviesListViewModelImpl(repository, CurrentTimeProviderImpl()) as T
}
