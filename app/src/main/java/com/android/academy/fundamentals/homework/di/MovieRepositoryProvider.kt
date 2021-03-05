package com.android.academy.fundamentals.homework.di

import com.android.academy.fundamentals.homework.domain.MovieRepository

internal interface MovieRepositoryProvider {
    fun provideMovieRepository(): MovieRepository
}
