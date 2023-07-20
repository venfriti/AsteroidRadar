package com.android.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.asteroidradar.models.Asteroid
import com.android.asteroidradar.models.PictureOfDay


@Database(entities = [Asteroid::class, PictureOfDay::class], version = 8, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {

    abstract fun asteroidDao() : AsteroidDao
    abstract fun pictureDao() : PictureDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        fun getInstance(context: Context) : AsteroidDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidDatabase::class.java,
                    "asteroid_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }
}