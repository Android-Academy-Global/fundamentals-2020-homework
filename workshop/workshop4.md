# Workshop 4

WIP In this workshop we will test ------

## TODO 4.0

- Open `MoviesListItemMapperTest.kt`
- Create new empty test method inside of `MoviesListItemMapperTest`
- Create `mapper` and `movie` model

```kotlin
class MoviesListItemMapperTest {
	@Test
	fun `map movie that's released today`() {
		val mapper = createMapper()

		val movie = createMovie()
	}
}
```

## TODO 4.1

- Open `Movie.kt`
- Uncomment all code marked with TODO comments So class will look like this:

```kotlin
data class Movie(
    ...
    val releaseDate: LocalDate
)
```

## TODO 4.2

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

## TODO 4.3

- Open `MoviesListItem.kt`
- Add `val release: NativeText` to constructor of `MoviesListItem`
  So class will look like this:

```kotlin
data class MoviesListItem(
    ...
    val release: NativeText
)
```

## TODO 4.4

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

## TODO 4.5
- Open `MovieFactory.kt`
- Add parameter to factory method

So method will look like:

```kotlin
fun createMovie(
    ...
    releaseDate : LocalDate = LocalDate.now()
): Movie {
    return Movie(
        ...
        releaseDate = releaseDate
    )
}
```

## TODO 4.6

- Open `MoviesListItemMapperTest.kt`
- Modify `map movie that’s released today` test method to

Change

```kotlin
fun `map movie that's released today`() {
    ...
    val movie = createMovie()
    ...
}
```

to

```kotlin
fun `map movie that's released today`() {
    ...
    val movie = createMovie(releaseDate = LocalDate.of(2021, Month.NOVEMBER, 1))
    ...
}
```

- Map `movie` with `mapper` : `val listItem = mapper.map(movie)`
- Add assertEquals to check `listItem.release` for equality
  with `NativeText.Resource(R.string.movies_list_released_today)`

So method will look like:

```kotlin
fun `map movie that's released today`() {
    ...
    val movie = createMovie(releaseDate = LocalDate.of(2021, Month.NOVEMBER, 1))
    ...
	val listItem = mapper.map(movie)
    assertEquals(NativeText.Resource(R.string.movies_list_released_today), listItem.release)
}
```

## TODO 4.7

- Run test and see result. Proceed to next step.

## TODO 4.8

- Open `MoviesListItemMapper .kt`

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

## TODO 4.9

- Run test again to ensure everything works now.

## TODO 4.10

- Open `MoviesListItemMapperTest.kt`
- Create new test method

```kotlin
 @Test
fun `map movie that's released 50 days ago`() {
    val mapper = createMapper()
    val movie = createMovie(releaseDate = LocalDate.of(2021, Month.SEPTEMBER, 12))

    val listItem = mapper.map(movie)

    assertEquals(
        NativeText.Plural(R.plurals.movies_list_days_before_release, 50, listOf(50)),
        listItem.release
    )
}
```

## TODO 4.11

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
            })
    }
}
```

## TODO 4.12

- Run test again to ensure everything works now.

## TODO 4.13 Refactor

- Open `MoviesListItemMapper.kt`
- Add `private val currentTimeProvider: CurrentTimeProvider` to constructor:

```kotlin
class MoviesListItemMapper(
    private val currentTimeProvider: CurrentTimeProvider
) {
    ...
}
```

## TODO 4.14

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

## TODO 4.15

- Open `MoviesListItemMapperTest.kt`
- Create `CurrentTimeProviderStub`:

```kotlin
class MoviesListItemMapperTest {
    private val testToday = LocalDateTime.of(2021, Month.SEPTEMBER, 29, 12, 0, 0, 0)
    private val currentTimeProviderStub = CurrentTimeProviderStub(testToday)
    ...
}
```

- Provide `CurrentTimeProviderStub` into `MoviesListItemMapper` constructor:

Change

```kotlin
class MoviesListItemMapperTest {
    ...
    private fun createMapper() = MoviesListItemMapper()
    ...
}
```

to

```kotlin
class MoviesListItemMapperTest {
    ...
    private fun createMapper() = MoviesListItemMapper(currentTimeProviderStub)
    ...
}
```

## TODO 4.16

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

## TODO 4.17

- Open `MoviesListViewModelTest.kt`

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
    private fun createMoviesListViewModel(repository: MovieRepository): MoviesListViewModel =
        MoviesListViewModelImpl(
            repository,
            MoviesListItemMapper(
                CurrentTimeProviderStub(LocalDateTime.of(2021, Month.SEPTEMBER, 29, 12, 0, 0))
            )
        )
}
```

## TODO 4.18

- Open `MoviesListItemMapperTest.kt`

- Create new test method

```kotlin
@Test
fun `map movie that will be released tomorrow`() {
    val mapper = createMapper()
    val movie = createMovie(releaseDate = LocalDate.of(2021, Month.NOVEMBER, 2))

    val listItem = mapper.map(movie)

    assertEquals(
        NativeText.Plural(R.plurals.movies_list_days_before_release, 1, listOf(1)),
        listItem.release
    )
}
```

## TODO 4.19

- Open `MoviesListItemMapper.kt`
- Process check when date is after today:

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

## TODO 4.20

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