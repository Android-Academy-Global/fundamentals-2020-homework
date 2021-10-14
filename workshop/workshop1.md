# Workshop 1

In first workshop we will create simple test for `MoviesListItemMapper`

- Open `MoviesListItemMapperTest` file

## TODO 1.1

- Create instance of `MoviesListItemMapper`

 ```kotlin 
 val mapper = MoviesListItemMapper()
 ```

## TODO 1.2

- Create or uncomment `Movie` instance

```kotlin
val movie = Movie(
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

## TODO 1.3

- Use created `MovieListItemMapper` to map `Movie` into `MoviesListItem`

```kotlin
val listItem = mapper.map(movie)
```

## TODO 1.4

- Create instance of `MoviesListItem` with same data as in `Movie` model

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

## TODO 1.5

- Use `assertEquals([expected], [actual])` to check equality of objects
- `expected` - model, you've created in **1.4**
- `actual` - mapped model, you've got in **1.3**

## TODO 1.6

- Run test and see green result