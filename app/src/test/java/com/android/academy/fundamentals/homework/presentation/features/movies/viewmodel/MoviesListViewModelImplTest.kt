package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.common.model.Result
import com.android.academy.fundamentals.homework.common.model.Success
import com.android.academy.fundamentals.homework.domain.MovieRepository
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails
import com.android.academy.fundamentals.homework.utils.viewModelTestingRules
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class MoviesListViewModelImplTest {

    @get:Rule
    val viewModelRule = viewModelTestingRules()

    @Test
    fun `moviesStateOutput by default converts movie to movie list item`() {
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
            )
        )
        val repository: MovieRepository = StubMovieRepository(movies = movies)

        val viewModel = MoviesListViewModelImpl(repository)

        val state = viewModel.moviesStateOutput.value
        assertThat((state as MoviesListViewState.MoviesLoaded).movies)
            .containsExactly(
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
                )
            )
    }

    internal class StubMovieRepository(
        val movies: List<Movie> = emptyList()
    ) : MovieRepository {
        override suspend fun loadMovies(): Result<List<Movie>> = Success(movies)
        override suspend fun loadMovie(movieId: Int): Result<MovieDetails> =
            TODO("repository doesn't follow Interface Segregation Principle")
    }
}
