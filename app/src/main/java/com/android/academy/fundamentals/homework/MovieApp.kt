package com.android.academy.fundamentals.homework

import android.app.Application
import com.android.academy.fundamentals.homework.data.locale.room.AppRoomDatabase

class MovieApp : Application() {

    companion object {
        lateinit var db: AppRoomDatabase
    }

    override fun onCreate() {
        super.onCreate()
        db = AppRoomDatabase.getInstance(this)
    }
}