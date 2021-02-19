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
    suspend fun fetchConfiguration(): ConfigurationResponse

    /**
     * Get the list of official genres for movies.
     */
    @GET("genre/movie/list")
    suspend fun fetchGenres(): GenresResponse

    /**
     * Get a list of movies in theatres. This is a release type query that looks for all movies that have a release
     * type of 2 or 3 within the specified date range.
     */
    @GET("movie/now_playong")
    suspend fun nowPlayong(): PlayongResponse

    @GET("movie/popular")
    suspend fun loadPopular(): PopularResponse

    @GET("movie/top_rated")
    suspend fun loadTopRated(): PopularResponse

    /**
     * use one of these methods for receiving films. You can use all of these if you want to show different categories of films.
     */
    @GET("movie/upcoming")
    suspend fun fetchUpcoming(
        @Query("page") page: Int
    ): UpComingResponse
    /**
     * for receiving movie details information.
     */
    @GET("movie/{movie_id}")
    suspend fun fetchMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDetailsResponse

    /**
     * Get the cast and crew for a movie.
     */
    @GET("movie/{movie_id}/credits")
    suspend fun fetchMovieCasts(
        @Path("movie_id") movieId: Int
    ): MovieCastResponse

    /**
     * for receiving information about some people.
     */
    @GET("person/{person_id}")
    suspend fun fetchPersonInfo(
        @Path("person_id") personId: String
    ): PersonInfoResponse

    /**
     * for searching films by user search request. Use it if you want additional task for your experience.
     */
    @GET("search/movie")
    suspend fun searchMovie(
    ): MovieResponse
}