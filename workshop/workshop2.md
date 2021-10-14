# Workshop 2

In this workshop we will test `ViewModel`

- Open `MoviesListViewModelImplTest.kt`

## TODO 2.1

- Create instance of `StubMovieRepository`

```kotlin
val repository = StubMovieRepository(movies = movies)
```

## TODO 2.2

- Create `MoviesListViewModelImpl` and provide `movies` list and `MoviesListItemMapper ` into
  constructor.

```kotlin
val viewModel = MoviesListViewModelImpl(repository, MoviesListItemMapper())
```

## TODO 2.3

- Get `moviesStateOutput` from our `ViewModel`
- Cast output to `MoviesListViewState.MoviesLoaded` type

```kotlin
val movieLoadedState = viewModel.moviesStateOutput.value as MoviesListViewState.MoviesLoaded
```

## TODO 2.4

- Use `assertEquals` to check movies from `mappedMovieList` and `movieLoadedState`

```kotlin
assertEquals(mappedMovieList, movieLoadedState)
```

## TODO 2.5

- Run Test and after you see the result, proceed to the next step.

## TODO 2.6

- Uncomment next Rule and Run Test again

```kotlin
 @get:Rule
val viewModelRule = viewModelTestingRules()
```



