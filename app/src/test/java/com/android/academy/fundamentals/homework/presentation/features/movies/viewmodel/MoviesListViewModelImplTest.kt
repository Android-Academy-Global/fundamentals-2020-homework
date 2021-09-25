package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
 * Первые тесты: отображение списка, ошибка
 *
 * Пишем просто в лоб, дорабатывать будем позже.
 * Что можно продемонстрировать:
 * - junit4, сейчас актуален junit5 (но есть нюанс)
 * - Как добавить первый тест (директории)
 * - Правила именования класса для тестов
 * - Именование теста
 * - Структура теста G-W-T
 * - Тестировать тест (проверять на заведомо ложном значении)
 * - Тестироание корутин, переопределение диспетчера
 * - moviesStateOutput.postValue (InstantTaskExecutorRule)
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
            Movie(id = 671039, title = "Bronx", reviewCount = 200, isLiked = false, rating = 0, pgAge = 0, genres = emptyList(), runningTime = 0, imageUrl = null),
            Movie(id = 724989, title = "Hard Kill", reviewCount = 151, isLiked = false, rating = 0, pgAge = 0, genres = emptyList(), runningTime = 0, imageUrl = null),
            Movie(id = 400160, title = "The SpongeBob Movie: Sponge on the Run", reviewCount = 1395, isLiked = false, rating = 0, pgAge = 0, genres = emptyList(), runningTime = 0, imageUrl = null),
        )
        val repository = StubMovieRepository(movies = movies)

        val viewModel = MoviesListViewModelImpl(repository)

        val state = viewModel.moviesStateOutput.value
        assertThat((state as MoviesListViewState.MoviesLoaded).movies).isEqualTo(movies)
    }

    class StubMovieRepository(
        val movies: List<Movie> = emptyList()
    ) : MovieRepository {
        override suspend fun loadMovies(): Result<List<Movie>> = Success(movies)
        override suspend fun loadMovie(movieId: Int): Result<MovieDetails> = TODO("Not yet implemented")
    }
}
