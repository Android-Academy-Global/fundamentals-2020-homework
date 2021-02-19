package com.android.academy.fundamentals.homework.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PersonInfoResponse(
    @SerialName("birthday") val birthday : String,
    @SerialName("known_for_department") val knownForDepartment : String,
    @SerialName("deathday") val deathday : String,
    @SerialName("id") val id : Int,
    @SerialName("name") val name : String,
    @SerialName("also_known_as") val alsoKnownAs : List<String>,
    @SerialName("gender") val gender : Int,
    @SerialName("biography") val biography : String,
    @SerialName("popularity") val popularity : Double,
    @SerialName("place_of_birth") val placeOfBirth : String,
    @SerialName("profile_path") val profilePath : String,
    @SerialName("adult") val adult : Boolean,
    @SerialName("imdb_id") val imdbId : String,
    @SerialName("homepage") val homepage : String
)
