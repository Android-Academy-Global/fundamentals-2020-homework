package com.android.academy.fundamentals.homework.presentation.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.fundamentals.homework.R
import com.android.academy.fundamentals.homework.data.locale.json.JsonStorage
import com.android.academy.fundamentals.homework.data.remote.retrofit.RetrofitStorage
import com.android.academy.fundamentals.homework.di.MovieRepositoryProvider
import com.android.academy.fundamentals.homework.di.NetworkModule
import com.android.academy.fundamentals.homework.presentation.features.moviedetails.view.MovieDetailsFragment
import com.android.academy.fundamentals.homework.presentation.features.movies.view.MoviesListFragment
import com.android.academy.fundamentals.homework.repository.MovieRepository
import com.android.academy.fundamentals.homework.repository.MovieRepositoryImpl

class MainActivity : AppCompatActivity(),
                     MoviesListFragment.MoviesListItemClickListener,
                     MovieDetailsFragment.MovieDetailsBackClickListener,
                     MovieRepositoryProvider {

    private val networkModule = NetworkModule()
    private val remoteDataSource = RetrofitStorage(networkModule.api)
    private val localDataSource = JsonStorage(this)
    private val movieRepository = MovieRepositoryImpl(localDataSource, remoteDataSource)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            routeToMoviesList()
        }
    }

    override fun onMovieSelected(movieId: Int) {
        routeToMovieDetails(movieId)
    }

    override fun onMovieDeselected() {
        routeBack()
    }

    private fun routeToMoviesList() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                MoviesListFragment.create(),
                MoviesListFragment::class.java.simpleName
            )
            .addToBackStack("trans:${MoviesListFragment::class.java.simpleName}")
            .commit()
    }

    private fun routeToMovieDetails(movieId: Int) {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                MovieDetailsFragment.create(movieId),
                MovieDetailsFragment::class.java.simpleName
            )
            .addToBackStack("trans:${MovieDetailsFragment::class.java.simpleName}")
            .commit()
    }

    private fun routeBack() {
        supportFragmentManager.popBackStack()
    }

    override fun provideMovieRepository(): MovieRepository = movieRepository
}