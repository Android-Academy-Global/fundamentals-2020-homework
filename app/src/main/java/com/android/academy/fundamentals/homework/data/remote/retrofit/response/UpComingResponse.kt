package com.android.academy.fundamentals.homework.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UpComingResponse(
    @SerialName("page") val page : Int,
    @SerialName("results") val results : List<ResultResponse>,
    @SerialName("dates") val dates : DatesResponse,
    @SerialName("total_results") val totalResults : Int,
    @SerialName("total_pages") val totalPages : Int
)