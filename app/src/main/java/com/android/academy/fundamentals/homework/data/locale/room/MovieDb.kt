package com.android.academy.fundamentals.homework.data.locale.room

import androidx.room.Entity

@Entity(
    tableName = "Movie",
    primaryKeys = ["id"]
)
data class MovieDb(
    val id: Int,
    val pgAge: Int,
    val title: String,
    val runningTime: Int,
    val reviewCount: Int,
    val isLiked: Boolean,
    val rating: Int,
    val imageUrl: String?
)