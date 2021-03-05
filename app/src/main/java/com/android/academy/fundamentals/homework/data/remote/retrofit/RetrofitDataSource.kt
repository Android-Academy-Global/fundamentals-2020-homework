package com.android.academy.fundamentals.homework.data.remote.retrofit

import com.android.academy.fundamentals.homework.data.remote.RemoteDataSource
import com.android.academy.fundamentals.homework.model.Actor
import com.android.academy.fundamentals.homework.model.Genre
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails

private const val ADULT_AGE = 16
private const val CHILD_AGE = 13

internal class RetrofitDataSource(
    private val api: MovieApiService,
    private val imageUrlAppender: ImageUrlAppender,
) : RemoteDataSource {

    override suspend fun loadMovies(): List<Movie> {
        val genres = api.loadGenres().genres
        // TODO пагинация
        return api.loadUpcoming(page = 1).results.map { movie ->
            Movie(
                id = movie.id,
                title = movie.title,
                imageUrl = imageUrlAppender.getMoviePosterImageUrl(movie.posterPath),
                rating = movie.voteAverage.toInt(),
                reviewCount = movie.voteCount,
                pgAge = movie.adult.toSpectatorAge(),
                runningTime = 100,
                isLiked = false,
                genres = genres
                    .filter { genreResponse -> movie.genreIds.contains(genreResponse.id) }
                    .map { genre -> Genre(genre.id, genre.name) },
            )
        }
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails {
        val details = api.loadMovieDetails(movieId)

        return MovieDetails(
            id = details.id,
            pgAge = details.adult.toSpectatorAge(),
            title = details.title,
            genres = details.genres.map { Genre(it.id, it.name) },
            reviewCount = details.revenue,
            isLiked = false,
            rating = details.popularity.toInt(),
            detailImageUrl = imageUrlAppender.getDetailImageUrl(details.backdropPath),
            storyLine = details.overview.orEmpty(),
            actors = api.loadMovieCredits(movieId).casts.map { actor ->
                Actor(
                    id = actor.id,
                    name = actor.name,
                    imageUrl = imageUrlAppender.getActorImageUrl(actor.profilePath)
                )
            }
        )
    }

    private fun Boolean.toSpectatorAge(): Int = if (this) ADULT_AGE else CHILD_AGE

}
