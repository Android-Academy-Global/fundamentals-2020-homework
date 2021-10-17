# Workshop 3

- Now we can refactor and simplify code from Workshop 1-2

## TODO 3.1

- Create `MovieFactory.kt` file in `test/****/utils` package
- Create or copy-paste `createMovie(**)` factory method

```kotlin
fun createMovie(
    id: Int = 0,
    title: String = "",
    reviewCount: Int = 0,
    isLiked: Boolean = false,
    rating: Int = 0,
    pgAge: Int = 0,
    genres: List<Genre> = emptyList(),
    runningTime: Int = 0,
    imageUrl: String? = null
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
        imageUrl = imageUrl
    )
}
```

## TODO 3.2 MoviesListItemMapperTest

- Open `MoviesListItemMapperTest.kt`
- Instead of constructor for `Movie` use `createMovie()` factory method:
  Instead of

```kotlin
val movie = Movie(**)
```

use

```kotlin
val movie = createMovie(
    id = 671039,
    title = "Test 1",
    reviewCount = 200,
    isLiked = true,
    pgAge = 15,
    runningTime = 55,
    imageUrl = "test url"
)
```

## TODO 3.3

- Instead of `assertEquals` for whole `MoviesListItem` model use one `assertEquals()` for each
  parameter (it's easier to add new fields in the future):

- Instead of

```kotlin
val expectedMovieListItem = MoviesListItem(
    id = 671039,
    title = "Test 1",
    reviewCount = 200,
    isLiked = true,
    pgAge = 15,
    runningTime = 55,
    imageUrl = "test url",
    genres = listOf(),
    rating = 4
)
```

use

```kotlin
assertEquals(671039, mappedItem.id)
assertEquals("Test 1", mappedItem.title)
assertEquals(200, mappedItem.reviewCount)
assertEquals(true, mappedItem.isLiked)
assertEquals(15, mappedItem.pgAge)
assertEquals(55, mappedItem.runningTime)
assertEquals("test url", mappedItem.imageUrl)
```

It lets you add new fields easier in the future.

## TODO 3.4 MoviesListViewModelTest

- Open `MoviesListViewModelTest.kt`
- Create `createMapper()` factory method inside of `MoviesListViewModelTest.kt` and use it instead
  of `MoviesListItemMapper` constructor

```kotlin
 private fun createMapper() = MoviesListItemMapper()
```

## TODO 3.5

Because we already tested `MoviesListItemMapper` and created `createMovie(***)` factory method, we
can simplify this code for creating `Movie`'s model only with id.

- Instead of `Movie` constructors use `createMovie(id = [1,2,...])` factory methods

```kotlin
val movies = listOf(
    createMovie(id = 1),
    createMovie(id = 2)
)
```

## TODO 3.6

- Remove `val mappedMovieList = listOf(***)` - now, when we check only `id`'s, whole list creation
  is not needed

## TODO 3.7

Now we can check only `id`'s from viewmodel's output and remove previous `assertEquals()` method:

- Safely cast viewmodel's output to `MoviesListViewState.MoviesLoaded`

```kotlin
val movieLoadedState = viewModel.moviesStateOutput.value as? MoviesListViewState.MoviesLoaded
```

- Map `movieLoadedState` to it `id`'s and then use `assertEquals` with simple `listOf(1,2)`

```kotlin
assertEquals(
    listOf(1, 2),
    movieLoadedState?.movies?.map { it.id }
)
```

## TODO 3.8

- Create factory method for `viewModel`

```kotlin
  private fun createMoviesListViewModel(repository: MovieRepository): MoviesListViewModel =
    MoviesListViewModelImpl(repository, MoviesListItemMapper())
```

- Use factory instead of `MoviesListViewModelImpl` constructor:

## TODO 3.9

- Run all refactored tests to check them works

# Workshop 3

- Now we can refactor and simplify code from Workshop 1-2

## TODO 3.1

- Create `MovieFactory.kt` file in `test/****/utils` package
- Create or copy-paste `createMovie(**)` factory method

```kotlin
fun createMovie(
    id: Int = 0,
    title: String = "",
    reviewCount: Int = 0,
    isLiked: Boolean = false,
    rating: Int = 0,
    pgAge: Int = 0,
    genres: List<Genre> = emptyList(),
    runningTime: Int = 0,
    imageUrl: String? = null
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
        imageUrl = imageUrl
    )
}
```

## TODO 3.2 MoviesListItemMapperTest

- Open `MoviesListItemMapperTest.kt`
- Instead of constructor for `Movie` use `createMovie()` factory method:
  Instead of

```kotlin
val movie = Movie(**)
```

use

```kotlin
val movie = createMovie(
    id = 671039,
    title = "Test 1",
    reviewCount = 200,
    isLiked = true,
    pgAge = 15,
    runningTime = 55,
    imageUrl = "test url"
)
```

## TODO 3.3

- Instead of `assertEquals` for whole `MoviesListItem` model use one `assertEquals()` for each
  parameter (it's easier to add new fields in the future):

- Instead of

```kotlin
val expectedMovieListItem = MoviesListItem(
    id = 671039,
    title = "Test 1",
    reviewCount = 200,
    isLiked = true,
    pgAge = 15,
    runningTime = 55,
    imageUrl = "test url",
    genres = listOf(),
    rating = 4
)
```

use

```kotlin
assertEquals(671039, mappedItem.id)
assertEquals("Test 1", mappedItem.title)
assertEquals(200, mappedItem.reviewCount)
assertEquals(true, mappedItem.isLiked)
assertEquals(15, mappedItem.pgAge)
assertEquals(55, mappedItem.runningTime)
assertEquals("test url", mappedItem.imageUrl)
```

It lets you add new fields easier in the future.

## TODO 3.4 MoviesListViewModelTest

- Open `MoviesListViewModelTest.kt`
- Create `createMapper()` factory method inside of `MoviesListViewModelTest.kt` and use it instead
  of `MoviesListItemMapper` constructor

```kotlin
 private fun createMapper() = MoviesListItemMapper()
```

## TODO 3.5

Because we already tested `MoviesListItemMapper` and created `createMovie(***)` factory method, we
can simplify this code for creating `Movie`'s model only with id.

- Instead of `Movie` constructors use `createMovie(id = [1,2,...])` factory methods

```kotlin
val movies = listOf(
    createMovie(id = 1),
    createMovie(id = 2)
)
```

## TODO 3.6

- Remove `val mappedMovieList = listOf(***)` - now, when we check only `id`'s, whole list creation
  is not needed

## TODO 3.7

Now we can check only `id`'s from viewmodel's output and remove previous `assertEquals()` method:

- Safely cast viewmodel's output to `MoviesListViewState.MoviesLoaded`

```kotlin
val movieLoadedState = viewModel.moviesStateOutput.value as? MoviesListViewState.MoviesLoaded
```

- Map `movieLoadedState` to it `id`'s and then use `assertEquals` with simple `listOf(1,2)`

```kotlin
assertEquals(
    listOf(1, 2),
    movieLoadedState?.movies?.map { it.id }
)
```

## TODO 3.8

- Create factory method for `viewModel`

```kotlin
  private fun createMoviesListViewModel(repository: MovieRepository): MoviesListViewModel =
    MoviesListViewModelImpl(repository, MoviesListItemMapper())
```

- Use factory instead of `MoviesListViewModelImpl` constructor:

## TODO 3.9

- Run all refactored tests to check them works

# Summary

- `MoviesListItemMapperTest.kt`

```kotlin
package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.model.createMovie
import org.junit.Assert.assertEquals
import org.junit.Test

class MoviesListItemMapperTest {

    @Test
    fun `mapper maps some fields as is`() {
        val mapper = MoviesListItemMapper()
        val movie = createMovie(
            id = 671039,
            title = "Test 1",
            reviewCount = 200,
            isLiked = true,
            pgAge = 15,
            runningTime = 55,
            imageUrl = "test url",
            genres = listOf(),
            rating = 4
        )

        val listItem = mapper.map(movie)

        assertEquals(671039, listItem.id)
        assertEquals("Test 1", listItem.title)
        assertEquals(200, listItem.reviewCount)
        assertEquals(true, listItem.isLiked)
        assertEquals(15, listItem.pgAge)
        assertEquals(55, listItem.runningTime)
        assertEquals("test url", listItem.imageUrl)
    }
}
```

- `MoviesListViewModelTest.kt`

```kotlin
package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.domain.MovieRepository
import com.android.academy.fundamentals.homework.domain.StubMovieRepository
import com.android.academy.fundamentals.homework.model.createMovie
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
            createMovie(id = 1),
            createMovie(id = 2)
        )

        val repository = StubMovieRepository()
        repository.setResult(movies)
        val viewModel = createMoviesListViewModel(repository)

        val movieLoadedState =
            viewModel.moviesStateOutput.value as? MoviesListViewState.MoviesLoaded

        assertEquals(
            listOf(1, 2),
            movieLoadedState?.movies?.map { it.id }
        )
    }

    @Test
    fun `moviesStateOutput on error returns failure`() {
        val repository = StubMovieRepository()
        repository.setErrorResult()
        val viewModel = createMoviesListViewModel(repository)
        assertEquals(MoviesListViewState.FailedToLoad, viewModel.moviesStateOutput.value)
    }

    private fun createMoviesListViewModel(repository: MovieRepository): MoviesListViewModel =
        MoviesListViewModelImpl(repository, MoviesListItemMapper())
}
```



