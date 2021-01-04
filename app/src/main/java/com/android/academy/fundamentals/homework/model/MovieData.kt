package com.android.academy.fundamentals.homework.model

import java.io.Serializable

data class MovieData(
    val id: Int,
    val pgAge: Int,
    val title: String,
    val genres: List<GenreData>,
    val runningTime: Int,
    val reviewCount: Int,
    val isLiked: Boolean,
    val rating: Int,
    val imageUrl: String,
    val detailImageUrl: String,
    val storyLine: String,
    val actors: List<ActorData>

) : Serializable