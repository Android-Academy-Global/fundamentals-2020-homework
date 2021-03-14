package com.android.academy.fundamentals.homework.data.locale.room

import com.android.academy.fundamentals.homework.data.locale.LocalDataSource
import com.android.academy.fundamentals.homework.model.Actor
import com.android.academy.fundamentals.homework.model.Genre
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails

class RoomDataSource(private val db: AppRoomDatabase): LocalDataSource {

    override suspend fun loadMovies(): List<Movie> {
        return db.getMovieDao().getMovies()
            .map { movieFull ->
                Movie(
                    id = movieFull.movie.id,
                    pgAge = movieFull.movie.pgAge,
                    title = movieFull.movie.title,
                    genres = movieFull.genres.map { genre ->
                        Genre(genre.id, genre.name)
                    },
                    runningTime = movieFull.movie.runningTime,
                    reviewCount = movieFull.movie.reviewCount,
                    isLiked = movieFull.movie.isLiked,
                    rating = movieFull.movie.rating,
                    imageUrl = movieFull.movie.imageUrl
                )
            }
    }

    override fun insertMovies(movieFromNetwork: List<Movie>) {
        val movies = movieFromNetwork.map { movie ->
            MovieDb(
                id = movie.id,
                pgAge = movie.pgAge,
                title = movie.title,
                runningTime = movie.runningTime,
                reviewCount = movie.reviewCount,
                isLiked = movie.isLiked,
                rating = movie.rating,
                imageUrl = movie.imageUrl
            )
        }
        db.getMovieDao().insertMovies(movies)
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails {
        val movieDetailsDb = db.getMovieDetailsDao().getMovieDetails()
        return MovieDetails(
            id = movieDetailsDb.details.parentId,
            pgAge = movieDetailsDb.details.pgAge,
            title = movieDetailsDb.details.title,
            genres = movieDetailsDb.genres.map { Genre(it.id, it.name) },
            reviewCount = movieDetailsDb.details.reviewCount,
            isLiked = false,
            rating = movieDetailsDb.details.rating,
            detailImageUrl = movieDetailsDb.details.detailImageUrl,
            storyLine = movieDetailsDb.details.storyLine,
            actors = movieDetailsDb.actors.map { actor ->
                Actor(
                    id = actor.id,
                    name = actor.name,
                    imageUrl = actor.imageUrl
                )
            }
        )
    }

    override fun insertMovieDetails(movieDetailsFromNetwork: Movie) {
        TODO("Not yet implemented")
    }
}