package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.common.model.Failure
import com.android.academy.fundamentals.homework.common.model.Result
import com.android.academy.fundamentals.homework.common.model.Success
import com.android.academy.fundamentals.homework.domain.MovieRepository
import com.android.academy.fundamentals.homework.model.Genre
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails
import com.android.academy.fundamentals.homework.model.createMovie
import com.android.academy.fundamentals.homework.utils.viewModelTestingRules
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MoviesListViewModelImplTest {
    @get:Rule
    val viewModelRule = viewModelTestingRules()

    @Test
    fun `moviesStateOutput by default returns movies list`() {
        val movies = listOf(
            createMovie(id = 1),
            createMovie(id = 2)
        )
        val repository = StubMovieRepository()
        repository.setResult(movies)

        val viewModel = createMoviesListViewModel(repository)

        val state = viewModel.moviesStateOutput.value
        assertTrue(
            state is MoviesListViewState.MoviesLoaded,
            "state is $state"
        )
        val loadedMovies = state.movies.map { it.id }
        assertEquals(listOf(1, 2), loadedMovies)
    }

    @Test
    fun `moviesStateOutput on error returns failure`() {
        val repository = StubMovieRepository()
        repository.setErrorResult()

        val viewModel = createMoviesListViewModel(repository)

        val state = viewModel.moviesStateOutput.value
        assertTrue(
            state is MoviesListViewState.FailedToLoad,
            "state is $state"
        )
    }

    internal class StubMovieRepository : MovieRepository {

        private var result: Result<List<Movie>> = Success(emptyList())

        fun setResult(movies: List<Movie>) {
            result = Success(movies)
        }

        fun setErrorResult() {
            result = Failure(Throwable())
        }

        override suspend fun loadMovies(): Result<List<Movie>> = result
        override suspend fun loadMovie(movieId: Int): Result<MovieDetails> =
            TODO("repository doesn't follow Interface Segregation Principle")
    }


    private fun createMoviesListViewModel(repository: MovieRepository): MoviesListViewModel =
        MoviesListViewModelImpl(repository)
}