package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.common.model.Failure
import com.android.academy.fundamentals.homework.common.model.Result
import com.android.academy.fundamentals.homework.common.model.Success
import com.android.academy.fundamentals.homework.domain.MovieRepository
import com.android.academy.fundamentals.homework.model.Genre
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails
import com.android.academy.fundamentals.homework.utils.viewModelTestingRules
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class MoviesListViewModelImplTest {
    @get:Rule
    val viewModelRule = viewModelTestingRules()

    @Test
    fun `moviesStateOutput by default returns movies list`() {
        val movies = listOf(
            Movie(
                id = 671039,
                title = "Bronx",
                reviewCount = 200,
                isLiked = false,
                rating = 4,
                pgAge = 13,
                genres = emptyList(),
                runningTime = 34,
                imageUrl = "test url"
            ),
            Movie(
                id = 724989,
                title = "Hard Kill",
                reviewCount = 151,
                isLiked = false,
                rating = 0,
                pgAge = 0,
                genres = listOf(Genre(1, "test")),
                runningTime = 0,
                imageUrl = null
            ),
            Movie(
                id = 400160,
                title = "The SpongeBob Movie: Sponge on the Run",
                reviewCount = 1395,
                isLiked = false,
                rating = 0,
                pgAge = 0,
                genres = emptyList(),
                runningTime = 0,
                imageUrl = null
            ),
        )
        val repository = StubMovieRepository()
        repository.setResult(movies)

        val viewModel = MoviesListViewModelImpl(repository, MoviesListItemMapper())

        val state = viewModel.moviesStateOutput.value
        assertThat((state as MoviesListViewState.MoviesLoaded).movies)
            .isEqualTo(
                listOf(
                    MoviesListItem(
                        id = 671039,
                        title = "Bronx",
                        reviewCount = 200,
                        isLiked = false,
                        rating = 4,
                        pgAge = 13,
                        genres = emptyList(),
                        runningTime = 34,
                        imageUrl = "test url"
                    ),
                    MoviesListItem(
                        id = 724989,
                        title = "Hard Kill",
                        reviewCount = 151,
                        isLiked = false,
                        rating = 0,
                        pgAge = 0,
                        genres = listOf(Genre(1, "test")),
                        runningTime = 0,
                        imageUrl = null
                    ),
                    MoviesListItem(
                        id = 400160,
                        title = "The SpongeBob Movie: Sponge on the Run",
                        reviewCount = 1395,
                        isLiked = false,
                        rating = 0,
                        pgAge = 0,
                        genres = emptyList(),
                        runningTime = 0,
                        imageUrl = null
                    )
                )
            )
    }

    @Test
    fun `moviesStateOutput on error returns failure`() {
        val repository = StubMovieRepository()
        repository.setErrorResult()

        val viewModel = MoviesListViewModelImpl(repository, MoviesListItemMapper())

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
}