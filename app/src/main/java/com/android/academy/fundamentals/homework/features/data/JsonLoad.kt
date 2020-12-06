package com.android.academy.fundamentals.homework.features.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader


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
    val overview: String,
    val adult: Boolean
)

private suspend fun loadGenres(context: Context): List<Genre> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "genres.json")
    val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
    jsonGenres.map { Genre(id = it.id, name = it.name) }
}

private fun readAssetFileToString(context: Context, fileName: String): String {
    val stream = context.assets.open(fileName)
    return stream.bufferedReader().use(BufferedReader::readText)
}

private suspend fun loadActors(context: Context): List<Actor> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "people.json")
    val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)
    jsonActors.map { Actor(id = it.id, name = it.name, picture = it.profilePicture) }
}

internal suspend fun loadMovies(context: Context): List<Movie> = withContext(Dispatchers.IO) {
    val genresMap = loadGenres(context).associateBy { it.id }
    val actorsMap = loadActors(context).associateBy { it.id }

    val data = readAssetFileToString(context, "data.json")
    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

    jsonMovies.map { jsonMovie ->
        Movie(
            id = jsonMovie.id,
            title = jsonMovie.title,
            overview = jsonMovie.overview,
            poster = jsonMovie.posterPicture,
            backdrop = jsonMovie.backdropPicture,
            ratings = jsonMovie.ratings,
            adult = jsonMovie.adult,
            runtime = jsonMovie.runtime,
            genres = jsonMovie.genreIds.map {
                genresMap[it] ?: throw IllegalArgumentException("Genre not found")
            },
            actors = jsonMovie.actors.map {
                actorsMap[it] ?: throw IllegalArgumentException("Actor not found")
            }
        )
    }
}