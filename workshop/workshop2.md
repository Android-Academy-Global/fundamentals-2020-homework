# Workshop 2

In this workshop we will test `ViewModel` and `LiveData`

## TODO 2.0

- Open `MoviesListViewModel.kt`
- Press `Cmd+Shift+T` or `Ctrl+Shift+T` and press `Select new test...`
- Select `/test/` directory, not `/androidTest/`
- Create empty  `MoviesListViewModelTest` (maybe you will need to remove some unnecesary code)
- Also create `Rule` (use already created `viewModelTestingRules()`) for running code in single
  thread

```kotlin
class MoviesListViewModelTest {
    @get:Rule
    val viewModelRule = viewModelTestingRules()
}
```

## TODO 2.1: Success Test Case

- Create new empty test method inside of `MoviesListViewModelTest`:

```kotlin
@Test
fun `moviesStateOutput by default returns movies list`() {

}
```

- Put code from **TODO 2.2-2.8** into this method

## TODO 2.2

- Create or copy-paste list of `Movie`

```kotlin
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
```

## TODO 2.3

- Create instance of `StubMovieRepository` and provide created `Movie` list

```kotlin
val repository = StubMovieRepository(movies = movies)
```

## TODO 2.4

- Create `MoviesListViewModelImpl` and provide `Movie` list and `MoviesListItemMapper ` into
  constructor.

```kotlin
val viewModel = MoviesListViewModelImpl(repository, MoviesListItemMapper())
```

## TODO 2.5

- Create or copy-paste list of expected `MoviesListItem`

```kotlin
val mappedMovieList = listOf(
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
```

## TODO 2.6

- Get `movieLoadedState` from our `ViewModel`
- Create `expectedState` from created `Movie` list

```kotlin
 val movieLoadedState = viewModel.moviesStateOutput.value
val expectedState = MoviesListViewState.MoviesLoaded(mappedMovieList)       
```

## TODO 2.7

- Use `assertEquals` to check `expectedState` and `movieLoadedState` equality

```kotlin
assertEquals(expectedState, movieLoadedState)
```

## TODO 2.8

- Run Test and see green result. Then proceed to next step.

## TODO 2.9: Failure Test Case

Now let's add new test for Failure scenario:

- Create new test and new instance of ```StubMovieRepository```

```kotlin
@Test
fun `moviesStateOutput on error returns failure`() {
    val repository = StubMovieRepository()
}
```

## TODO 2.10

Now wee need to refactor `StubMovieRepository` to give us ability to set different results in
different test cases

- Open `StubMovieRepository.kt`
- Remove `val movies: List<Movie> = emptyList()` from `StubMovieRepository` constuctor
- Add next code to `StubMovieRepository`:

```kotlin
private var result: Result<List<Movie>> = Success(emptyList())

fun setResult(movies: List<Movie>) {
    result = Success(movies)
}

fun setErrorResult() {
    result = Failure(Throwable())
}
```

- Change

```kotlin
override suspend fun loadMovies(): Result<List<Movie>> = Success(movies)
```

to

```kotlin
override suspend fun loadMovies(): Result<List<Movie>> = result
```

- Fix first test: remove `movies` from constructor and use `setResult(movies)` method
  on `StubMovieRepository`

## TODO 2.11

- Return back to new test in `MoviesListViewModelTest`
- Add `setErrorResult()` to repository
- Create `MoviesListViewModelImpl` same way you did in previous test

## TODO 2.12

- Use `assertEquals` to check that value of `viewModel.moviesStateOutput`
  is `MoviesListViewState.FailedToLoad`

```kotlin
assertEquals(
    MoviesListViewState.FailedToLoad,
    viewModel.moviesStateOutput.value
)

```

## TODO 2.13

- Run test and see green result

## TODO 2.14: Test your tests

- Try breaking something in the implementation to see that tests became red.
- If the tests stay green when the implementation is broken then you have an error in tests.

# Summary

- `MoviesListViewModelTest.kt`

```kotlin
package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.domain.StubMovieRepository
import com.android.academy.fundamentals.homework.model.Genre
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.utils.viewModelTestingRules
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MoviesListViewModelTest {
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

        val mappedMovieList = listOf(
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

        val movieLoadedState = viewModel.moviesStateOutput.value
        val expectedState = MoviesListViewState.MoviesLoaded(mappedMovieList)

        assertEquals(expectedState, movieLoadedState)
    }

    @Test
    fun `moviesStateOutput on error returns failure`() {
        val repository = StubMovieRepository()
        repository.setErrorResult()
        val viewModel = MoviesListViewModelImpl(repository, MoviesListItemMapper())
        assertEquals(MoviesListViewState.FailedToLoad, viewModel.moviesStateOutput.value)
    }
}
```

- `StubMovieRepository.kt`

```kotlin
package com.android.academy.fundamentals.homework.domain

import com.android.academy.fundamentals.homework.common.model.Failure
import com.android.academy.fundamentals.homework.common.model.Result
import com.android.academy.fundamentals.homework.common.model.Success
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails

internal class StubMovieRepository : MovieRepository {
    override suspend fun loadMovies(): Result<List<Movie>> = result
    override suspend fun loadMovie(movieId: Int): Result<MovieDetails> =
        TODO("Stub repository doesn't implement loadMovie method")

    private var result: Result<List<Movie>> = Success(emptyList())

    fun setResult(movies: List<Movie>) {
        result = Success(movies)
    }

    fun setErrorResult() {
        result = Failure(Throwable())
    }
}
```