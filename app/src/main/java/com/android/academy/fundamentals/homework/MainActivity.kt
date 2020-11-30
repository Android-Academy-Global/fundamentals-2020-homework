package com.android.academy.fundamentals.homework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.fundamentals.homework.features.moviedetails.MovieDetailsFragment
import com.android.academy.fundamentals.homework.features.movies.MoviesListFragment

class MainActivity : AppCompatActivity(),
    MoviesListFragment.MoviesListItemClickListener,
    MovieDetailsFragment.MovieDetailsBackClickListener
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            routeToMoviesList()
        }
    }

    override fun onMovieSelected() {
        routeToMovieDetails()
    }

    override fun onMovieDeselected() {
        routeBack()
    }

    private fun routeToMoviesList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MoviesListFragment.create(), MoviesListFragment::class.java.simpleName)
            .addToBackStack("trans:${MoviesListFragment::class.java.simpleName}")
            .commit()
    }

    private fun routeToMovieDetails() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, MovieDetailsFragment.create(), MovieDetailsFragment::class.java.simpleName)
            .addToBackStack("trans:${MovieDetailsFragment::class.java.simpleName}")
            .commit()
    }

    private fun routeBack() {
        supportFragmentManager.popBackStack()
    }
}