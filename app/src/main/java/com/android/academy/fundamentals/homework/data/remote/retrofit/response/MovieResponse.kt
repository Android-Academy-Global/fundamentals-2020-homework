package com.android.academy.fundamentals.homework.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieResponse(
    @SerialName("poster_path") val posterPath : String,
    @SerialName("adult") val adult : Boolean,
    @SerialName("genre_ids") val genreIds : List<Int>,
    @SerialName("id") val id : Int,
    @SerialName("title") val title : String,
    @SerialName("vote_count") val voteCount : Int,
    @SerialName("vote_average") val voteAverage : Double
)