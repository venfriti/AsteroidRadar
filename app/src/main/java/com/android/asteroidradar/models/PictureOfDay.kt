package com.android.asteroidradar.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "picture_database")
data class PictureOfDay(
    @ColumnInfo(name = "media_type") @Json(name = "media_type") val mediaType: String? ="",
    @ColumnInfo(name = "title") val title: String,
    @PrimaryKey val url: String)