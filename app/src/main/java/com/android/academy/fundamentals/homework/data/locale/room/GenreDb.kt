package com.android.academy.fundamentals.homework.data.locale.room

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "Genre",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDb::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class GenreDb(
    val id: Int,
    val name: String,
    val parentId: Int
)