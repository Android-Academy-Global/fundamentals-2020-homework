package com.android.academy.fundamentals.homework.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieResponse(
    @SerialName("page") val page : Int,
    @SerialName("results") val results : List<ResultResponse>,
    @SerialName("total_results") val totalResults : Int,
    @SerialName("total_pages") val totalPages : Int
)
