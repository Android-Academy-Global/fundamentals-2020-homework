package com.android.academy.fundamentals.homework.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CastResponse(
    @SerialName("adult") val adult: Boolean,
    @SerialName("gender") val gender: Int?,
    @SerialName("id") val id: Int,
    @SerialName("known_for_department") val knownForDepartment: String,
    @SerialName("name") val name: String,
    @SerialName("original_name") val originalName: String,
    @SerialName("popularity") val popularity: Double,
    @SerialName("profile_path") val profilePath: String?,
    @SerialName("cast_id") val castId: Int,
    @SerialName("character") val character: String,
    @SerialName("credit_id") val creditId: String,
    @SerialName("order") val order: Int
)