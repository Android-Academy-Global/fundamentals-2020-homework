# Workshop 4

In this workshop we will implement the "Days before/after release" feature using the Test Driven Development.

# TODO 4.0 Add `releaseDate` field to `Movie` model

The `movie` model needs more data to support the feature.
We will add the relese date field in this section.

## TODO 4.0.0

- Open `Movie.kt`
- Uncomment all code marked with TODO comments So class will look like this:

```kotlin
data class Movie(
    ...
    val releaseDate: LocalDate
)
```

## TODO 4.0.1

- Open `RetrofitDataSource.kt`
- Uncomment all code marked with TODO comments

So `loadMovies` method will look like this:

```kotlin
override suspend fun loadMovies(): List<Movie> {
    val genres = api.loadGenres().genres
    return api.loadUpcoming(page = 1).results.map { movie ->
        Movie(
            ...
            releaseDate = LocalDate.parse(movie.releaseDate)
        )
    }
}
```

## TODO 4.0.2

- Open `MoviesListItem.kt`
- Add `val release: NativeText` to constructor of `MoviesListItem`
  So class will look like this:

```kotlin
data class MoviesListItem(
    ...
    val release: NativeText
)
```

## TODO 4.0.3

- Open `MoviesListItemMapper.kt`
- Add mapping of `release` field to hardcoded `NativeText.Simple("")`
  So class will look like this:

```kotlin
class MoviesListItemMapper {
    fun map(movie: Movie): MoviesListItem {
        return MoviesListItem(
            ...
            release = NativeText.Simple("")
        )
    }
} 
```

## TODO 4.0.4

- Open `MovieFactory.kt`
- Add parameter to factory method

So method will look like:

```kotlin
fun createMovie(
    ...
    releaseDate: LocalDate = LocalDate.now()
): Movie {
    return Movie(
        ...
        releaseDate = releaseDate
    )
}
```

## TODO 4.0.5 

Run all unit tests to make sure that everyting works.

# TODO 4.1 Handle a movie that is released today

We're on a red stage. Our goal is to create a test that tests today's movie mapping. The test should fail because the case isn't implemented yet.

## TODO 4.1.0

- Open `MoviesListItemMapperTest.kt`
- Create new empty test method inside of `MoviesListItemMapperTest`
- Create `mapper` and `movie` model

```kotlin
class MoviesListItemMapperTest {
    
    ...

    @Test
    fun `map movie that's released today`() {
        val mapper = createMapper()
        val movie = createMovie()
    }
}
```

## TODO 4.1.1

- Map the `movie` with the `mapper` 

```kotlin
fun `map movie that's released today`() {
    ...

    val listItem = mapper.map(movie)
}
```

## TODO 4.1.2

- Add assertEquals to check `listItem.release` for equality
  with `NativeText.Resource(R.string.movies_list_released_today)`

```kotlin
fun `map movie that's released today`() {
    ...

    val listItem = mapper.map(movie)

    assertEquals(NativeText.Resource(R.string.movies_list_released_today), listItem.release)
}
```

## TODO 4.1.3

- Run the test and see the result.
The test should fail.
Now you can move from a Red to a Green stage.

## TODO 4.1.4

- Open `MoviesListItemMapper.kt`

Change:

```kotlin
class MoviesListItemMapper {
    fun map(movie: Movie): MoviesListItem {
        return MoviesListItem(
            ...
            release = NativeText.Simple("")
        )
    }
} 
```

to

```kotlin
class MoviesListItemMapper {
    fun map(movie: Movie): MoviesListItem {
        return MoviesListItem(
            ...
            release = NativeText.Resource(R.string.movies_list_released_today)
        )
    }
} 
```

## TODO 4.1.5

- Run test again to ensure everything works now.

# TODO 4.2 Let's create test to test mapping of movie, released 50 days ago

## TODO 4.2.0

- Open `MoviesListItemMapperTest.kt`
- Create new test method

```kotlin
 @Test
fun `map movie that's released 50 days ago`() {
    val mapper = createMapper()
    val movie = createMovie(releaseDate = LocalDate.of(2021, Month.SEPTEMBER, 12))

    val listItem = mapper.map(movie)

    assertEquals(
        NativeText.Plural(R.plurals.movies_list_days_after_release, 50, listOf(50)),
        listItem.release
    )
}
```
## TODO 4.2.1

- Run test and see result. Proceed to next step.

## TODO 4.2.2

- Open `MoviesListItemMapper.kt`
- Add logic to mapping `release` field:
  
Change

```kotlin
class MoviesListItemMapper {
    fun map(movie: Movie): MoviesListItem {
        return MoviesListItem(
            ...
            release = NativeText.Resource(R.string.movies_list_released_today)
        )
    }
}
```

to

```kotlin
class MoviesListItemMapper {
    fun map(movie: Movie): MoviesListItem {
        return MoviesListItem(
            ...
            release = when {
                movie.releaseDate.isBefore(LocalDate.now()) -> {
                    val period = ChronoUnit.DAYS.between(movie.releaseDate, LocalDate.now()).toInt()
                    NativeText.Plural(R.plurals.movies_list_days_after_release, period, listOf(period))
                }
                else -> NativeText.Resource(R.string.movies_list_released_today)
            }
        )
    }
}
```

## TODO 4.2.3

- Run test again to ensure everything works now.

# TODO 4.3 Let's refactor some previous code

## TODO 4.3.0

- Open `MoviesListItemMapper.kt`
- Add `private val currentTimeProvider: CurrentTimeProvider` to constructor:

```kotlin
class MoviesListItemMapper(
    private val currentTimeProvider: CurrentTimeProvider
) {
    ...
}
```

## TODO 4.3.1

- Extract logic of mapping to `release` field into method:

```kotlin
private fun mapReleaseDate(movie: Movie): NativeText {
    val today = currentTimeProvider.getCurrentTime().toLocalDate()
    return when {
        movie.releaseDate.isBefore(today) -> {
            val period = ChronoUnit.DAYS.between(movie.releaseDate, today).toInt()
            NativeText.Plural(
                R.plurals.movies_list_days_after_release,
                period,
                listOf(period)
            )
        }
        else -> NativeText.Resource(R.string.movies_list_released_today)
    }
}
```

So class will look like

```kotlin
class MoviesListItemMapper(
    private val currentTimeProvider: CurrentTimeProvider
) {
    fun map(movie: Movie): MoviesListItem {
        return MoviesListItem(
            ...
            release = mapReleaseDate(movie)
        )
    }
```

## TODO 4.3.2

- Open `MoviesListItemMapperTest.kt`
- Change `createMapper()` method to provide `LocalDateTime`
  
Change

```kotlin
    private fun createMapper() = MoviesListItemMapper()
```

to

```kotlin
    private fun createMapper(
        currentTime: LocalDateTime = LocalDateTime.of(2021, Month.NOVEMBER, 1, 12, 0, 0, 0)
    ) = MoviesListItemMapper(CurrentTimeProviderStub(currentTime))
```

## TODO 4.3.3

- Open `MovieListViewModelFactory.kt`

- Provide `CurrentTimeProviderImpl()` into `MoviesListItemMapper()`:

Change

```kotlin
@Suppress("UNCHECKED_CAST")
internal class MovieListViewModelFactory(...) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MoviesListViewModelImpl(repository, MoviesListItemMapper()) as T
}
```

to

```kotlin
@Suppress("UNCHECKED_CAST")
internal class MovieListViewModelFactory(...) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MoviesListViewModelImpl(repository, MoviesListItemMapper(CurrentTimeProviderImpl())) as T
}
```

## TODO 4.3.4

- Open `MoviesListViewModelTest.kt`
- Provide `CurrentTimeProviderImpl()` into `createMoviesListViewModel()` factory method:

Change

```kotlin
class MoviesListViewModelTest {
    ...
    private fun createMoviesListViewModel(repository: MovieRepository): MoviesListViewModel =
        MoviesListViewModelImpl(repository, MoviesListItemMapper())
}
```

to

```kotlin
class MoviesListViewModelTest {
    ...
    private fun createMoviesListViewModel(
        repository: MovieRepository,
        currentTime: LocalDateTime = LocalDateTime.of(2021, Month.NOVEMBER, 1, 12, 0, 0)
    ): MoviesListViewModel =
        MoviesListViewModelImpl(
            repository,
            MoviesListItemMapper(CurrentTimeProviderStub(currentTime))
        )
}
```

## TODO 4.3.5

- Run tests again to ensure everything still works

# TODO 4.4 Let's create test to check mapping of movie, released tomorrow

## TODO 4.4.0

- Open `MoviesListItemMapperTest.kt`

- Create new test method

```kotlin
@Test
fun `map movie that will be released tomorrow`() {
    val mapper = createMapper(LocalDateTime.of(2021, Month.NOVEMBER, 1, 12, 0, 0))
    val movie = createMovie(releaseDate = LocalDate.of(2021, Month.NOVEMBER, 2))

    val listItem = mapper.map(movie)

    assertEquals(
        NativeText.Plural(R.plurals.movies_list_days_before_release, 1, listOf(1)),
        listItem.release
    )
}
```

## TODO 4.4.1

- Run test and see result. Proceed to next step.

## TODO 4.4.2

- Open `MoviesListItemMapper.kt`
- Add case when `movie.releaseDate` is after `today` parameter

```kotlin
private fun mapReleaseDate(movie: Movie): NativeText {
    val today = currentTimeProvider.getCurrentTime().toLocalDate()
    return when {
        movie.releaseDate.isBefore(today) -> {
            ...
        }
        movie.releaseDate.isAfter(today) -> {
            val period = ChronoUnit.DAYS.between(today, movie.releaseDate).toInt()
            NativeText.Plural(
                R.plurals.movies_list_days_before_release,
                period,
                listOf(period)
            )
        }
        else -> ...
    }
}
```

## TODO 4.4.3

- Run test to ensure everything is working.

# TODO 4.5 Feature is ready! You only need to show it on UI

## TODO 4.5.0

- Open `MoviesListAdapter`

- Set `item.release` value to `movieRelease.text`:

- Delete or comment `movieRelease.visibility = View.GONE`

```kotlin
class MoviesListAdapter(private val onClickCard: (movieId: Int) -> Unit) :
    ListAdapter<MoviesListItem, MoviesListAdapter.ViewHolder>(DiffCallback()) {
    ...
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        ...
        fun bind(item: MoviesListItem, onClickCard: (movieId: Int) -> Unit) {
            ...
            movieRelease.text = item.release.toCharSequence(context)
            //movieRelease.visibility = View.GONE
            ...
        }
        ...
    }
}
```

## TODO 4.5.1

- Run application and check new test-driven-developed feature