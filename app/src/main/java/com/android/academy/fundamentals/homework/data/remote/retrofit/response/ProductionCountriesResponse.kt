package com.android.academy.fundamentals.homework.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ProductionCountriesResponse(
    @SerialName("iso_3166_1") val iso_3166_1 : String,
    @SerialName("name") val name : String
)
