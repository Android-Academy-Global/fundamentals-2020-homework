package com.android.academy.fundamentals.homework.features.data

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Float,
    val adult: Boolean,
    val runtime: Int,
    val genres: List<Genre>,
    val actors: List<Actor>
)