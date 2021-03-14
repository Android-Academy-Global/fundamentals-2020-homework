package com.android.academy.fundamentals.homework.data.locale.room

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "Actor",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDb::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ActorDb(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val parentId: Int
)