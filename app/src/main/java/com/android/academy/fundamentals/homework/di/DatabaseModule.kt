package com.android.academy.fundamentals.homework.di

import android.content.Context
import androidx.room.Room
import com.android.academy.fundamentals.homework.data.locale.room.AppRoomDatabase

class DatabaseModule(context: Context) {

    val builder by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            AppRoomDatabase::class.java,
            "app_room_database"
        ).apply {
            fallbackToDestructiveMigration()
        }.build()
    }
}