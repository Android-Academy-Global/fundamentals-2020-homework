package com.android.academy.fundamentals.homework

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.fundamentals.homework.data.JsonMovieRepository
import com.android.academy.fundamentals.homework.data.MovieRepository
import com.android.academy.fundamentals.homework.di.MovieRepositoryProvider
import com.android.academy.fundamentals.homework.features.moviedetails.MovieDetailsFragment
import com.android.academy.fundamentals.homework.features.movies.MoviesListFragment
import com.android.academy.fundamentals.homework.model.Movie
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale

private const val ANIMATION_DURATION: Long = 2000
private const val LONG_ANIMATION_DURATION: Long = 3000

class MainActivity : AppCompatActivity(),
    MoviesListFragment.MoviesListItemClickListener,
    MovieDetailsFragment.MovieDetailsBackClickListener,
    MovieRepositoryProvider {

    private val movieDetailsSharedElementTransaction = MaterialContainerTransform().apply {
        scrimColor = Color.TRANSPARENT
        duration = ANIMATION_DURATION
    }

    private val movieListSharedElementExitTransition =  MaterialElevationScale(true)
        .setDuration( LONG_ANIMATION_DURATION)

    private val movieListSharedElementReenterTransition =  MaterialElevationScale(false)
        .setDuration( LONG_ANIMATION_DURATION)


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
        fragment.exitTransition = movieListSharedElementExitTransition

        fragment.reenterTransition = movieListSharedElementReenterTransition

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                fragment,
                MoviesListFragment::class.java.simpleName
            )
            .commit()
    }

    private fun routeToMovieDetails(movie: Movie, sharedElement: View) {
        val sharedElementTransitionName = sharedElement.transitionName

        val fragment = MovieDetailsFragment.create(movie.id, sharedElementTransitionName)

        fragment.sharedElementEnterTransition = movieDetailsSharedElementTransaction
        fragment.sharedElementReturnTransition = movieDetailsSharedElementTransaction

        supportFragmentManager.beginTransaction()
            .addSharedElement(sharedElement, sharedElementTransitionName)
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