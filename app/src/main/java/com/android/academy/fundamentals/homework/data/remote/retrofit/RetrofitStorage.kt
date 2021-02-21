package com.android.academy.fundamentals.homework.data.remote.retrofit

import com.android.academy.fundamentals.homework.data.remote.RemoteDataSource
import com.android.academy.fundamentals.homework.data.remote.retrofit.response.ImageResponse
import com.android.academy.fundamentals.homework.model.Actor
import com.android.academy.fundamentals.homework.model.Genre
import com.android.academy.fundamentals.homework.model.MovieDetails
import com.android.academy.fundamentals.homework.model.Movie

class RetrofitStorage(private val api: MovieApiService) : RemoteDataSource {

    private var imageResponse: ImageResponse? = null
    private var baseUrl: String? = null
    private var posterSize: String? = null
    private var backdropSize: String? = null
    private var profileSize: String? = null

    override suspend fun loadMovies(): List<Movie> {
        imageResponse = api.fetchConfiguration().images
        baseUrl = imageResponse?.baseUrl
        // TODO придумать более изящный вариант
        posterSize = imageResponse?.posterSizes?.find { it == "w500" }
        // TODO придумать более изящный вариант
        backdropSize = imageResponse?.backdropSizes?.find { it == "w780" }
        // TODO придумать более изящный вариант
        profileSize = imageResponse?.profileSizes?.find { it == "w185" }
        // TODO пагинация
        return api.fetchUpcoming(page = 1).results.map { movie ->
            Movie(
                id = movie.id,
                title = movie.title,
                imageUrl = baseUrl.orEmpty()
                    .plus(posterSize.orEmpty())
                    .plus(movie.posterPath),
                rating = movie.voteAverage.toInt(),
                reviewCount = movie.voteCount,
                pgAge = if (movie.adult) 16 else 13,
                runningTime = 100,
                isLiked = false,
                genres = api.fetchGenres().genres.map { genre ->
                    Genre(
                        genre.id,
                        genre.name
                    )
                },
            )
        }
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails {
        val details = api.fetchMovieDetails(movieId)
        return MovieDetails(id = details.id,
            pgAge = if (details.adult) 16 else 13,
            title = details.title,
            genres = details.genres.map { Genre(it.id, it.name) },
            reviewCount = details.revenue,
            isLiked = false,
            rating = details.popularity.toInt(),
            detailImageUrl = baseUrl.orEmpty()
                .plus(backdropSize.orEmpty())
                .plus(details.backdropPath.orEmpty()),
            storyLine = details.overview.orEmpty(),
            actors = api.fetchMovieCasts(movieId).casts.map { actor ->
                Actor(
                    id = actor.id,
                    name = actor.name,
                    imageUrl = baseUrl.orEmpty()
                        .plus(profileSize.orEmpty())
                        .plus(actor.profilePath.orEmpty())
                )
            })
    }
}