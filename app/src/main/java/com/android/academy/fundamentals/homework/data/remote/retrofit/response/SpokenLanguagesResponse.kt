package com.android.academy.fundamentals.homework.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SpokenLanguagesResponse(
    @SerialName("iso_639_1") val iso_639_1 : String,
    @SerialName("name") val name : String
)