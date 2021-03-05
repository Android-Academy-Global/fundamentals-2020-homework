package com.android.academy.fundamentals.homework.data.remote.retrofit

import com.android.academy.fundamentals.homework.data.remote.retrofit.response.ImageResponse
import kotlin.properties.Delegates

private const val DEFAULT_SIZE = "original"
private const val IDENTIFIER_SMALL_SIZE = "w185"
private const val IDENTIFIER_MEDIUM_SIZE = "w500"
private const val IDENTIFIER_LARGE_SIZE = "w780"

internal class ImageUrlAppender(private val api: MovieApiService) {

    private var imageResponse: ImageResponse? = null
    private var baseUrl: String? = null
    private var posterSize: String by Delegates.notNull()
    private var backdropSize: String by Delegates.notNull()
    private var profileSize: String by Delegates.notNull()

    suspend fun getDetailImageUrl(backdropPath: String?): String? {
        loadConfiguration()

        return buildUrl(baseUrl, backdropSize, backdropPath)
    }

    suspend fun getActorImageUrl(actorProfilePath: String?): String? {
        loadConfiguration()

        return buildUrl(baseUrl, profileSize, actorProfilePath)
    }

    suspend fun getMoviePosterImageUrl(moviePosterPath: String?): String? {
        loadConfiguration()

        return buildUrl(baseUrl, posterSize, moviePosterPath)
    }

    private suspend fun loadConfiguration() {
        if (imageResponse != null) return

        imageResponse = api.loadConfiguration().images
        baseUrl = imageResponse?.secureBaseUrl
        posterSize = imageResponse?.posterSizes?.find { size -> size == IDENTIFIER_MEDIUM_SIZE } ?: DEFAULT_SIZE
        backdropSize = imageResponse?.backdropSizes?.find { size -> size == IDENTIFIER_LARGE_SIZE } ?: DEFAULT_SIZE
        profileSize = imageResponse?.profileSizes?.find { size -> size == IDENTIFIER_SMALL_SIZE } ?: DEFAULT_SIZE
    }

    private fun buildUrl(url: String?, size: String, path: String?): String? {
        return if (url == null || path == null) {
            null
        } else {
            return "$url$size$path"
        }
    }
}
