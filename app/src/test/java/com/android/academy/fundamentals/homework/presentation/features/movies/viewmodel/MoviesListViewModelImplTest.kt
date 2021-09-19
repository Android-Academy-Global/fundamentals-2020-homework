package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.academy.fundamentals.homework.common.model.Failure
import com.android.academy.fundamentals.homework.common.model.Result
import com.android.academy.fundamentals.homework.common.model.Success
import com.android.academy.fundamentals.homework.domain.MovieRepository
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Рефакторинг тестов
 *
 * Уменьшим количество мест, которые подвержены изменениям в коде.
 * Что можно продемонстрировать:
 * - Фабричные методы, чтобы упросить инициализацию и избавиться от дублирования.
 * - Доработать стабы, убрать дублирование.
 */
@ExperimentalCoroutinesApi
internal class MoviesListViewModelImplTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `moviesStateOutput by default returns movies list`() = runBlockingTest {
        val movies = listOf(
            createMovie(id = 671039, title = "Bronx", reviewCount = 200),
            createMovie(id = 724989, title = "Hard Kill", reviewCount = 151),
            createMovie(id = 400160, title = "The SpongeBob Movie: Sponge on the Run", reviewCount = 1395),
        )
        val repository = StubMovieRepository(movies = movies)

        val viewModel = createMoviesListViewModel(repository)

        val state = viewModel.moviesStateOutput.value as MoviesListViewState.MoviesLoaded
        assertThat(state.movies).isEqualTo(movies)
    }

    @Test
    fun `moviesStateOutput on error returns failure`() = runBlockingTest {
        val repository = FailureMovieRepository(IllegalStateException())

        val viewModel = createMoviesListViewModel(repository)

        val state = viewModel.moviesStateOutput.value as MoviesListViewState.FailedToLoad
        assertThat(state.exception).isInstanceOf(IllegalStateException::class.java)
    }

    private fun createMoviesListViewModel(repository: MovieRepository): MoviesListViewModel =
        MoviesListViewModelImpl(repository)

    private fun createMovie(
        id: Int = 0,
        title: String = "",
        reviewCount: Int = 0
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
            imageUrl = null
        )
    }

    class StubMovieRepository(
        val movies: List<Movie> = emptyList()
    ) : MovieRepository {
        override suspend fun loadMovies(): Result<List<Movie>> = Success(movies)
        override suspend fun loadMovie(movieId: Int): Result<MovieDetails> = TODO("Not yet implemented")
    }

    class FailureMovieRepository(
        val throwable: Throwable,
        private val origin: MovieRepository = StubMovieRepository()
    ) : MovieRepository by origin {
        override suspend fun loadMovies(): Result<List<Movie>> = Failure(throwable)
    }
}
