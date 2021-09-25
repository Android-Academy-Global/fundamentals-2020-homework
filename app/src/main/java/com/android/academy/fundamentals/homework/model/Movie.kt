package com.android.academy.fundamentals.homework.model

// TODO uncomment next line for workshop
//import java.time.LocalDate

data class Movie(
    val id: Int,
    val pgAge: Int,
    val title: String,
    val genres: List<Genre>,
    val runningTime: Int,
    val reviewCount: Int,
    val isLiked: Boolean,
    val rating: Int,
    val imageUrl: String?,
    // TODO uncomment next line for workshop
    //val releaseDate: LocalDate
)
