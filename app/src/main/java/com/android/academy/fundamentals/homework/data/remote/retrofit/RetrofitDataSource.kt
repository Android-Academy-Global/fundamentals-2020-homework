package com.android.academy.fundamentals.homework.data.remote.retrofit

import com.android.academy.fundamentals.homework.data.remote.RemoteDataSource
import com.android.academy.fundamentals.homework.data.remote.retrofit.response.ImageResponse
import com.android.academy.fundamentals.homework.model.Actor
import com.android.academy.fundamentals.homework.model.Genre
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails

private const val DEFAULT_SIZE = "original"

private const val ADULT_AGE = 16
private const val CHILD_AGE = 13

class RetrofitDataSource(private val api: MovieApiService) : RemoteDataSource {

    private var imageResponse: ImageResponse? = null
    private var baseUrl: String? = null
    private var posterSize: String? = null
    private var backdropSize: String? = null
    private var profileSize: String? = null

    override suspend fun loadMovies(): List<Movie> {
        loadConfiguration()

        val genres = api.loadGenres().genres
        // TODO пагинация
        return api.loadUpcoming(page = 1).results.map { movie ->
            Movie(
                id = movie.id,
                title = movie.title,
                imageUrl = formingUrl(baseUrl, posterSize, movie.posterPath),
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
        loadConfiguration()

        val details = api.loadMovieDetails(movieId)

        return MovieDetails(
            id = details.id,
            pgAge = details.adult.toSpectatorAge(),
            title = details.title,
            genres = details.genres.map { Genre(it.id, it.name) },
            reviewCount = details.revenue,
            isLiked = false,
            rating = details.popularity.toInt(),
            detailImageUrl = formingUrl(baseUrl, backdropSize, details.backdropPath),
            storyLine = details.overview.orEmpty(),
            actors = api.loadMovieCredits(movieId).casts.map { actor ->
                Actor(
                    id = actor.id,
                    name = actor.name,
                    imageUrl = formingUrl(baseUrl, profileSize, actor.profilePath)
                )
            }
        )
    }

    private fun Boolean.toSpectatorAge(): Int = if (this) ADULT_AGE else CHILD_AGE

    @Synchronized
    private suspend fun loadConfiguration() {
        if (imageResponse != null) return

        imageResponse = api.loadConfiguration().images
        baseUrl = imageResponse?.secureBaseUrl
        // TODO придумать более изящный вариант
        posterSize = imageResponse?.posterSizes?.find { it == "w500" }
        // TODO придумать более изящный вариант
        backdropSize = imageResponse?.backdropSizes?.find { it == "w780" }
        // TODO придумать более изящный вариант
        profileSize = imageResponse?.profileSizes?.find { it == "w185" }
    }

    private fun formingUrl(url: String?, size: String?, path: String?): String? {
        return if (url == null || path == null) {
            null
        } else {
            url.plus(size.takeUnless { it.isNullOrEmpty() } ?: DEFAULT_SIZE)
                .plus(path)
        }
    }
}
