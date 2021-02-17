package com.android.academy.fundamentals.homework

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.fundamentals.homework.data.JsonMovieRepository
import com.android.academy.fundamentals.homework.data.MovieRepository
import com.android.academy.fundamentals.homework.di.MovieRepositoryProvider
import com.android.academy.fundamentals.homework.features.moviedetails.MovieDetailsFragment
import com.android.academy.fundamentals.homework.features.movies.MoviesListFragment
import com.android.academy.fundamentals.homework.model.Movie
import com.google.android.material.transition.MaterialElevationScale

class MainActivity : AppCompatActivity(),
    MoviesListFragment.MoviesListItemClickListener,
    MovieDetailsFragment.MovieDetailsBackClickListener,
    MovieRepositoryProvider {

    private val jsonMovieRepository = JsonMovieRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            routeToMoviesList()
        }
    }

    override fun onMovieSelected(movie: Movie, sharedView: View) {
        routeToMovieDetails(movie, sharedView)
    }

    override fun onMovieDeselected() {
        routeBack()
    }

    private fun routeToMoviesList() {
        val fragment = MoviesListFragment.create()

        fragment.exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.movies_motion_duration_large).toLong()
        }
        fragment.reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.movies_motion_duration_large).toLong()
        }

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                fragment,
                MoviesListFragment::class.java.simpleName
            )
            .commit()
    }

    private fun routeToMovieDetails(movie: Movie, sharedElement: View) {
        val movieDetailTransitionName = getString(R.string.movie_detail_transition_name)

        val fragment = MovieDetailsFragment.create(movie.id)

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .addSharedElement(sharedElement, movieDetailTransitionName)
            .replace(
                R.id.container,
                fragment,
                MovieDetailsFragment::class.java.simpleName
            )
            .addToBackStack("trans:${MovieDetailsFragment::class.java.simpleName}")
            .commit()
    }

    private fun routeBack() {
        supportFragmentManager.popBackStack()
    }

    override fun provideMovieRepository(): MovieRepository = jsonMovieRepository
}