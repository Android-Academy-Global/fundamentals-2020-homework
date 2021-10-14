# Workshop 3

- Now we can refactor and simplify code from Workshop 1-2

## TODO 3.1

- Open `MovieFactory.kt` file and uncomment next code:

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

## TODO 3.2

- Open `MoviesListItemMapperTest.kt`
- Instead of constructor for `Movie` use `createMovie()` factory method:

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

- Instead of `assertEquals` for whole model use one `assertEquals()` for each parameter (it's easier
  to add new fields in the future):

```kotlin
assertEquals(671039, mappedItem.id)
assertEquals("Test 1", mappedItem.title)
assertEquals(200, mappedItem.reviewCount)
assertEquals(true, mappedItem.isLiked)
assertEquals(15, mappedItem.pgAge)
assertEquals(55, mappedItem.runningTime)
assertEquals("test url", mappedItem.imageUrl)
```

## TODO 3.4

- Uncomment `createMapper()` factory method and use it instead of `MoviesListItemMapper` constructor

```kotlin
 private fun createMapper() = MoviesListItemMapper()
```

## TODO 3.5

Because we already tested mapping and created factory method, we can simplify this code for creating
model only with id's.

- Instead of `Movie` constructors use `createMovie(id = [1])` factory methods

```kotlin
val movies = listOf(
    createMovie(id = 1),
    createMovie(id = 2)
)
```

## TODO 3.6

- Remove `mappedMovieList` - now, when we check only `id`'s, whole models are not needed

## TODO 3.7

Now we can check only `id`'s from `viewmodel`'s output and remove previous `assertEquals()` method:

```kotlin
val loadedIds = movieLoadedState.movies.map { it.id }
assertEquals(listOf(1, 2, ...), loadedIds)
```

## TODO 3.8

- Uncomment factory method for `viewModel`
- Use factory instead of `MoviesListViewModelImpl` constructor:

```kotlin
  private fun createMoviesListViewModel(repository: MovieRepository): MoviesListViewModel =
    MoviesListViewModelImpl(repository, MoviesListItemMapper())
```
   

