package com.android.academy.fundamentals.homework.data.locale.room

import androidx.room.Embedded
import androidx.room.Relation

class MovieWithGenres(
    @Embedded
    val movie: MovieDb,
    @Relation(parentColumn = "id", entityColumn = "parentId")
    val genres: List<GenreDb>
)