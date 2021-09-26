package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.R
import com.android.academy.fundamentals.homework.common.model.Failure
import com.android.academy.fundamentals.homework.common.model.Result
import com.android.academy.fundamentals.homework.common.model.Success
import com.android.academy.fundamentals.homework.common.text.NativeText
import com.android.academy.fundamentals.homework.common.time.CurrentTimeProviderStub
import com.android.academy.fundamentals.homework.domain.MovieRepository
import com.android.academy.fundamentals.homework.model.Genre
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails
import com.android.academy.fundamentals.homework.utils.viewModelTestingRules
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MoviesListViewModelImplTest {
    @get:Rule
    val viewModelRule = viewModelTestingRules()

    private val today = LocalDate.of(2021, 5, 25)
    private val currentTimeStub = CurrentTimeProviderStub(today.atTime(1, 1, 1))

    @Test
    fun `moviesStateOutput by default returns movies list`() {
        val movies = listOf(
            createMovie(
                id = 671039,
                title = "Test 1",
                reviewCount = 200,
                isLiked = true,
                pgAge = 15,
                runningTime = 55,
                imageUrl = "test url",
                releaseDate = today.plusDays(2)
            ),
            createMovie(
                id = 724989,
                title = "Hard Kill",
                reviewCount = 151,
                genres = listOf(Genre(1, "test")),
                rating = 55,
                releaseDate = today.minusDays(2)
            )
        )
        val repository = StubMovieRepository()
        repository.setResult(movies)

        val viewModel = createMoviesListViewModel(repository)

        val state = viewModel.moviesStateOutput.value
        assertTrue(
            state is MoviesListViewState.MoviesLoaded,
            "state is $state"
        )
        assertEquals(
            listOf(
                createMovieListItem(
                    id = 671039,
                    title = "Test 1",
                    reviewCount = 200,
                    isLiked = true,
                    pgAge = 15,
                    runningTime = 55,
                    imageUrl = "test url",
                    releaseAt = NativeText.Plural(
                        R.plurals.movies_list_days_before_release,
                        2,
                        listOf(2)
                    )
                ),
                createMovieListItem(
                    id = 724989,
                    title = "Hard Kill",
                    reviewCount = 151,
                    genres = listOf(Genre(1, "test")),
                    rating = 55,
                ),
            ),
            state.movies
        )
    }

    @Test
    fun `moviesStateOutput on error returns failure`() {
        val repository = StubMovieRepository()
        repository.setErrorResult()

        val viewModel = createMoviesListViewModel(repository)

        val state = viewModel.moviesStateOutput.value
        assertTrue(
            state is MoviesListViewState.FailedToLoad,
            "state after clicking something is $state"
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

    private fun createMovie(
        id: Int = 0,
        title: String = "",
        reviewCount: Int = 0,
        isLiked: Boolean = false,
        rating: Int = 0,
        pgAge: Int = 0,
        genres: List<Genre> = emptyList(),
        runningTime: Int = 0,
        imageUrl: String? = null,
        releaseDate: LocalDate = LocalDate.of(2021, 1, 1)
    ): Movie {
        return Movie(
            id = id,
            title = title,
            reviewCount = reviewCount,
            isLiked = false,
            rating = 0,
            pgAge = 0,
            genres = emptyList(),
            runningTime = 0,
            imageUrl = null,
            releaseDate = LocalDate.now()
        )
    }

    private fun createMovieListItem(
        id: Int = 0,
        title: String = "",
        reviewCount: Int = 0,
        isLiked: Boolean = false,
        rating: Int = 0,
        pgAge: Int = 0,
        genres: List<Genre> = emptyList(),
        runningTime: Int = 0,
        imageUrl: String? = null,
        releaseAt: NativeText = NativeText.Simple("")
    ): MoviesListItem {
        return MoviesListItem(
            id = id,
            title = title,
            reviewCount = reviewCount,
            isLiked = false,
            rating = 0,
            pgAge = 0,
            genres = emptyList(),
            runningTime = 0,
            imageUrl = null,
            releaseAt = releaseAt
        )
    }
}