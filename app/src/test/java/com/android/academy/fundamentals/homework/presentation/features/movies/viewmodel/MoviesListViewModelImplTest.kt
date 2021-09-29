package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.R
import com.android.academy.fundamentals.homework.common.model.Failure
import com.android.academy.fundamentals.homework.common.model.Result
import com.android.academy.fundamentals.homework.common.model.Success
import com.android.academy.fundamentals.homework.common.text.NativeText
import com.android.academy.fundamentals.homework.common.time.CurrentTimeProvider
import com.android.academy.fundamentals.homework.common.time.CurrentTimeProviderImpl
import com.android.academy.fundamentals.homework.domain.MovieRepository
import com.android.academy.fundamentals.homework.model.Genre
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails
import com.android.academy.fundamentals.homework.utils.viewModelTestingRules
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class MoviesListViewModelImplTest {

    @get:Rule
    val viewModelRule = viewModelTestingRules()

    @Test
    fun `moviesStateOutput by default converts movie to movie list item`() {
        val movies = listOf(
            createMovie(
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
        val repository: MovieRepository = StubMovieRepository()
            .apply { setResult(movies) }

        val viewModel = createViewModel(repository)

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
                    imageUrl = "test url",
                    // TODO нужно было изначально сравнивать построчно
                    release = NativeText.Simple("")
                )
            )
    }

    @Test
    fun `moviesStateOutput by default return list in the same order`() {
        val movies = listOf(
            createMovie(id = 671),
            createMovie(id = 1230),
            createMovie(id = 54),
        )
        val repository: MovieRepository = StubMovieRepository()
            .apply { setResult(movies) }

        val viewModel = createViewModel(repository)

        val state = viewModel.moviesStateOutput.value
        assertThat((state as MoviesListViewState.MoviesLoaded).movies.map { it.id })
            .containsExactly (671, 1230, 54)
    }

    @Test
    fun `moviesStateOutput if movie released now returns released today`() {
        val movies = listOf(
            createMovie(releaseDate = LocalDate.of(2021, 4, 21)),
        )
        val repository: MovieRepository = StubMovieRepository()
            .apply { setResult(movies) }
        val timeProvider = object : CurrentTimeProvider {
            override fun getCurrentTime(): LocalDateTime = LocalDateTime.of(2021, 4, 21, 15, 46)
        }

        val viewModel = createViewModel(repository, timeProvider)

        val state = viewModel.moviesStateOutput.value
        assertThat((state as MoviesListViewState.MoviesLoaded).movies.map { it.release })
                // TODO выглядит гадко, нужно все таки сделать отдельный маппер
            .contains(NativeText.Resource(R.string.movies_list_released_today))
    }

    @Test
    fun `moviesStateOutput in case of error returns failure`() {
        val repository = StubMovieRepository()
        repository.setErrorResult()

        val viewModel = createViewModel(repository)

        val state = viewModel.moviesStateOutput.value
        assertThat(state).isInstanceOf(MoviesListViewState.FailedToLoad::class.java)
    }

    // TODO это нужно было сделать при рефаторинге тестов
    private fun createViewModel(
        repository: MovieRepository,
        currentTimeProvider: CurrentTimeProvider = CurrentTimeProviderImpl()
    ): MoviesListViewModel = MoviesListViewModelImpl(repository, currentTimeProvider)

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
        releaseDate: LocalDate = LocalDate.now()
    ): Movie {
        return Movie(
            id = id,
            title = title,
            reviewCount = reviewCount,
            isLiked = isLiked,
            rating = rating,
            pgAge = pgAge,
            genres = genres,
            runningTime = runningTime,
            imageUrl = imageUrl,
            releaseDate = releaseDate
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
