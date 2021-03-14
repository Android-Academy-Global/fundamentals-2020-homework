package com.android.academy.fundamentals.homework.data.locale.room

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "MovieDetails",
    primaryKeys = ["parentId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDb::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieDetailsDb(
    val parentId: Int,
    val pgAge: Int,
    val title: String,
    val reviewCount: Int,
    val isLiked: Boolean,
    val rating: Int,
    val detailImageUrl: String?,
    val storyLine: String
)