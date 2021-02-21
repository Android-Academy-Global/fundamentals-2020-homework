package com.android.academy.fundamentals.homework.data.remote.retrofit

import com.android.academy.fundamentals.homework.data.remote.retrofit.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    /**
     * method for receiving app configurations, like base image URLs. You need this method for downloading images for films.
     */
    @GET("configuration")
    suspend fun loadConfiguration(): ConfigurationResponse

    /**
     * Get the list of official genres for movies.
     */
    @GET("genre/movie/list")
    suspend fun loadGenres(): GenresResponse

    /**
     * use one of these methods for receiving films. You can use all of these if you want to show different categories of films.
     */
    @GET("movie/upcoming")
    suspend fun loadUpcoming(
        @Query("page") page: Int
    ): UpComingResponse
    /**
     * for receiving movie details information.
     */
    @GET("movie/{movie_id}")
    suspend fun loadMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDetailsResponse

    /**
     * Get the cast and crew for a movie.
     */
    @GET("movie/{movie_id}/credits")
    suspend fun loadMovieCredits(
        @Path("movie_id") movieId: Int
    ): MovieCastResponse
}