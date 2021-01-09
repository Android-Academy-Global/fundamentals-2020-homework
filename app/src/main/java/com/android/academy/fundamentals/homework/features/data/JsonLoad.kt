package com.android.academy.fundamentals.homework.features.data

import android.content.Context
import com.android.academy.fundamentals.homework.model.Actor
import com.android.academy.fundamentals.homework.model.Genre
import com.android.academy.fundamentals.homework.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val jsonFormat = Json { ignoreUnknownKeys = true }

@Suppress("unused")
internal suspend fun loadMovies(context: Context): List<Movie> = withContext(Dispatchers.IO) {
    val genresMap = loadGenres(context)
    val actorsMap = loadActors(context)

    val data = readAssetFileToString(context, "data.json")
    parseMovies(data, genresMap, actorsMap)
}

private suspend fun loadGenres(context: Context): List<Genre> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "genres.json")
    parseGenres(data)
}

private fun readAssetFileToString(context: Context, fileName: String): String {
    val stream = context.assets.open(fileName)
    return stream.bufferedReader().readText()
}

internal fun parseGenres(jsonString: String): List<Genre> {
    val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(jsonString)
    return jsonGenres.map { jsonGenre -> Genre(id = jsonGenre.id, name = jsonGenre.name) }
}

private suspend fun loadActors(context: Context): List<Actor> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "people.json")
    parseActors(data)
}

internal fun parseActors(jsonString: String): List<Actor> {
    val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(jsonString)
    return jsonActors.map { jsonActor ->
        Actor(
            id = jsonActor.id,
            name = jsonActor.name,
            imageUrl = jsonActor.profilePicture
        )
    }
}

internal fun parseMovies(
    jsonString: String,
    genreData: List<Genre>,
    actors: List<Actor>
): List<Movie> {
    val genresMap = genreData.associateBy(Genre::id)
    val actorsMap = actors.associateBy(Actor::id)

    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(jsonString)

    return jsonMovies.map { jsonMovie ->
        @Suppress("unused")
        Movie(
            id = jsonMovie.id,
            title = jsonMovie.title,
            storyLine = jsonMovie.overview,
            imageUrl = jsonMovie.posterPicture,
            detailImageUrl = jsonMovie.backdropPicture,
            rating = (jsonMovie.ratings / 2).toInt(),
            reviewCount = jsonMovie.votesCount,
            pgAge = if (jsonMovie.adult) 16 else 13,
            runningTime = jsonMovie.runtime,
            genres = jsonMovie.genreIds.map { id ->
                genresMap[id].orThrow { IllegalArgumentException("Genre not found") }
            },
            actors = jsonMovie.actors.map { id ->
                actorsMap[id].orThrow { IllegalArgumentException("Actor not found") }
            },
            isLiked = false
        )
    }
}

private fun <T : Any> T?.orThrow(createThrowable: () -> Throwable): T {
    return this ?: throw createThrowable()
}
