package com.android.academy.fundamentals.homework.repository

import com.android.academy.fundamentals.homework.common.model.Result
import com.android.academy.fundamentals.homework.common.model.runCatchingResult
import com.android.academy.fundamentals.homework.data.locale.LocalDataSource
import com.android.academy.fundamentals.homework.data.remote.RemoteDataSource
import com.android.academy.fundamentals.homework.domain.MovieRepository
import com.android.academy.fundamentals.homework.model.Movie
import com.android.academy.fundamentals.homework.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class MovieRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : MovieRepository {

//    Plan:
//    Step 1. Connect Room to project
//    Step 2. Create DAOs for Movie objects
//    Step 3. When a screen opens, request the data from db first, send it to UI and only then request data from network and send it to the UI
//    Step 4. After loading the movies from the API - save them into the db, so they can be read in the future screen openings
//    Steps 5-6. Repeat steps 3-4 for the Movie Details screen

    override suspend fun loadMovies(): Result<List<Movie>> {
        return runCatchingResult {  withContext(Dispatchers.IO) {
            val movieDB = localDataSource.loadMovies()
            if (movieDB.isEmpty()) {
                val movieFromNetwork = remoteDataSource.loadMovies()
                localDataSource.insertMovies(movieFromNetwork)
                movieFromNetwork
            } else {
                movieDB
            }
        }
        }
    }

    override suspend fun loadMovie(movieId: Int): Result<MovieDetails> {
        return runCatchingResult { remoteDataSource.loadMovie(movieId) }
    }
}