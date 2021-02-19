package com.android.academy.fundamentals.homework.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatesResponse(
    @SerialName("maximum") val maximum : String,
    @SerialName("minimum") val minimum : String
)