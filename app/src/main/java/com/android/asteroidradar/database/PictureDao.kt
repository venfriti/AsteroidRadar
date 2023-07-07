package com.android.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.asteroidradar.models.PictureOfDay

@Dao
interface PictureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicture(picture: PictureOfDay)

    @Query("SELECT * FROM picture_database WHERE url=:url")
    suspend fun getPicture(url: String): PictureOfDay

    @Query("SELECT * FROM picture_database")
    suspend fun getSavedPicture(): PictureOfDay
}