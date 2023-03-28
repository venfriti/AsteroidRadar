package com.android.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.android.asteroidradar.api.NetworkRequest
import com.android.asteroidradar.api.getSeventhDay
import com.android.asteroidradar.api.getToday
import com.android.asteroidradar.api.parseAsteroidsJsonResult
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.android.asteroidradar.database.AsteroidDao
import com.android.asteroidradar.database.AsteroidDatabase
import com.android.asteroidradar.database.PictureDao
import org.json.JSONObject
import retrofit2.HttpException

class RefreshAsteroidsWork(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params){

    private val asteroidDao: AsteroidDao by lazy {
        AsteroidDatabase.getInstance(applicationContext).asteroidDao()
    }

    private val pictureDao: PictureDao by lazy {
        AsteroidDatabase.getInstance(applicationContext).pictureDao()
    }

    private val network: NetworkRequest by lazy { NetworkRequest() }

    companion object {
        const val WORK_NAME = "RefreshAsteroidWorker"
    }

    override suspend fun doWork(): Result {

        return try{
            val response = network.service.getAsteroids(
                getToday(),
                getSeventhDay()
            )

            val jsonObject: JsonObject = JsonParser.parseString(response.toString()).asJsonObject
            val jsonResult = JSONObject(jsonObject.toString())
            val listAsteroids = parseAsteroidsJsonResult(jsonResult)

            asteroidDao.insertAsteroids(listAsteroids)

            pictureDao.insertPicture(network.service.getPictureOfTheDay())

            Result.success()
        } catch (e: HttpException){
            Result.retry()
        }
    }
}