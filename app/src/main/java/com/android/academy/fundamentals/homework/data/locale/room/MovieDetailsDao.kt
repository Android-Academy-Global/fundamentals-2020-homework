package com.android.academy.fundamentals.homework.data.locale.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDetailsDao {

    @Query("SELECT * FROM MovieDetails")
    fun getMovieDetails(): MovieDetailsWithGenresAndActors

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetails(item: MovieDetailsDb)
}