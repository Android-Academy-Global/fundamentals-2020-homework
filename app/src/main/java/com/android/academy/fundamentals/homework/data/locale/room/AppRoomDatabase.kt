package com.android.academy.fundamentals.homework.data.locale.room

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MovieDb::class, MovieDetailsDb::class, GenreDb::class, ActorDb::class], version = 1
)
abstract class AppRoomDatabase : RoomDatabase() {

    companion object {

        private var INSTANCE: AppRoomDatabase? = null
        private const val DATABASE_NAME = "Movies.db"

        fun getInstance(context: Context): AppRoomDatabase {
            if (INSTANCE == null) {
                synchronized(AppRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        Log.d("TTT", "Creating new database instance")
                        INSTANCE = Room.databaseBuilder(
                            context,
                            AppRoomDatabase::class.java,
                            DATABASE_NAME
                        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                    }
                }
            }
            Log.d("TTT", "Getting the database instance")
            return INSTANCE!!
        }
    }

    abstract fun getMovieDao(): MovieDao
    abstract fun getMovieDetailsDao(): MovieDetailsDao
}