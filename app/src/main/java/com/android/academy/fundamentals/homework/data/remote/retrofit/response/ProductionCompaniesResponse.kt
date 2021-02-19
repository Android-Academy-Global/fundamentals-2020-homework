package com.android.academy.fundamentals.homework.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ProductionCompaniesResponse(
    @SerialName("id") val id : Int,
    @SerialName("logo_path") val logoPath : String?,
    @SerialName("name") val name : String,
    @SerialName("origin_country") val originCountry : String
)
