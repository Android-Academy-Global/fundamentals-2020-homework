package com.android.academy.fundamentals.homework.data.locale.room

import androidx.room.Embedded
import androidx.room.Relation

class MovieDetailsWithGenresAndActors(
    @Embedded
    val details: MovieDetailsDb,
    @Relation(parentColumn = "parentId", entityColumn = "parentId")
    val genres: List<GenreDb>,
    @Relation(parentColumn = "parentId", entityColumn = "parentId")
    val actors: List<ActorDb>
)