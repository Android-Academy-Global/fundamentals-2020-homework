package com.android.academy.fundamentals.homework.di

import com.android.academy.fundamentals.homework.repository.MovieRepository

internal interface MovieRepositoryProvider {
    fun provideMovieRepository(): MovieRepository
}