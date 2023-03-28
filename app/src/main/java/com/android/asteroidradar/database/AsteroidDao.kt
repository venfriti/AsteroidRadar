package com.android.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.asteroidradar.models.Asteroid


@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(asteroid: List<Asteroid>)

    @Query("SELECT * FROM asteroid_database")
    suspend fun getAllAsteroids() : List<Asteroid>

    @Query("SELECT * FROM asteroid_database WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate")
    suspend fun getWeekAsteroids(startDate: String, endDate: String): List<Asteroid>

    @Query("SELECT * FROM asteroid_database WHERE closeApproachDate = :today")
    suspend fun getTodayAsteroids(today: String): List<Asteroid>
}
