package com.android.academy.fundamentals.homework.features.data

import android.content.Context
import com.android.academy.fundamentals.homework.model.ActorData
import com.android.academy.fundamentals.homework.model.GenreData
import com.android.academy.fundamentals.homework.model.MovieData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.random.Random


private val jsonFormat = Json { ignoreUnknownKeys = true }

@Serializable
private class JsonGenre(val id: Int, val name: String)

@Serializable
private class JsonActor(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePicture: String
)

@Serializable
private class JsonMovie(
    val id: Int,
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("backdrop_path")
    val backdropPicture: String,
    val runtime: Int,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    val actors: List<Int>,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val votesCount: Int,
    val overview: String,
    val adult: Boolean
)

private suspend fun loadGenres(context: Context): List<GenreData> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "genres.json")
    parseGenres(data)
}

internal fun parseGenres(data: String): List<GenreData> {
    val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
    return jsonGenres.map { GenreData(id = it.id, name = it.name) }
}

private fun readAssetFileToString(context: Context, fileName: String): String {
    val stream = context.assets.open(fileName)
    return stream.bufferedReader().readText()
}

private suspend fun loadActors(context: Context): List<ActorData> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "people.json")
    parseActors(data)
}

internal fun parseActors(data: String): List<ActorData> {
    val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)
    return jsonActors.map { ActorData(id = it.id, name = it.name, imageUrl = it.profilePicture) }
}

@Suppress("unused")
internal suspend fun loadMovies(context: Context): List<MovieData> = withContext(Dispatchers.IO) {
    val genresMap = loadGenres(context)
    val actorsMap = loadActors(context)

    val data = readAssetFileToString(context, "data.json")
    parseMovies(data, genresMap, actorsMap)
}

internal fun parseMovies(
    data: String,
    genreData: List<GenreData>,
    actors: List<ActorData>
): List<MovieData> {
    val genresMap = genreData.associateBy { it.id }
    val actorsMap = actors.associateBy { it.id }

    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

    return jsonMovies.map { jsonMovie ->
        @Suppress("unused")
        MovieData(
            id = jsonMovie.id,
            title = jsonMovie.title,
            storyLine = jsonMovie.overview,
            imageUrl = jsonMovie.posterPicture,
            detailImageUrl = jsonMovie.backdropPicture,
            rating = (jsonMovie.ratings / 2).toInt(),
            reviewCount = jsonMovie.votesCount,
            pgAge = if (jsonMovie.adult) 16 else 13,
            runningTime = jsonMovie.runtime,
            genres = jsonMovie.genreIds.map {
                genresMap[it] ?: throw IllegalArgumentException("Genre not found")
            },
            actors = jsonMovie.actors.map {
                actorsMap[it] ?: throw IllegalArgumentException("Actor not found")
            },
            isLiked = Random.nextBoolean()
        )
    }
}