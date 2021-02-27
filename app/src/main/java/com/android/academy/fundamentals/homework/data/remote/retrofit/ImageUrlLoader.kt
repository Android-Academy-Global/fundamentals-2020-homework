package com.android.academy.fundamentals.homework.data.remote.retrofit

import com.android.academy.fundamentals.homework.data.remote.retrofit.response.ImageResponse

private const val DEFAULT_SIZE = "original"

class ImageUrlLoader(private val api: MovieApiService) {

    private var imageResponse: ImageResponse? = null
    private var baseUrl: String? = null
    private var posterSize: String? = null
    private var backdropSize: String? = null
    private var profileSize: String? = null

    suspend fun getDetailImageUrl(backdropPath: String?): String? {
        loadConfiguration()

        return formingUrl(baseUrl, backdropSize, backdropPath)
    }

    suspend fun getActorImageUrl(actorProfilePath: String?): String? {
        loadConfiguration()

        return formingUrl(baseUrl, profileSize, actorProfilePath)
    }

    suspend fun getMoviePosterImageUrl(moviePosterPath: String?): String? {
        loadConfiguration()

        return formingUrl(baseUrl, posterSize, moviePosterPath)
    }

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
