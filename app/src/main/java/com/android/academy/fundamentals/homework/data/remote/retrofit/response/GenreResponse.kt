package com.android.academy.fundamentals.homework.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GenreResponse(
    @SerialName("id") val id : Int,
    @SerialName("name") val name : String
)
