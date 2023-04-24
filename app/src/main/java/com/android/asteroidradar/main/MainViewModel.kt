package com.android.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.android.asteroidradar.api.NetworkRequest
import com.android.asteroidradar.api.getSeventhDay
import com.android.asteroidradar.api.getToday
import com.android.asteroidradar.api.parseAsteroidsJsonResult
import com.google.gson.JsonParser
import com.android.asteroidradar.models.Asteroid
import com.android.asteroidradar.models.PictureOfDay
import com.android.asteroidradar.database.AsteroidDao
import com.android.asteroidradar.database.AsteroidDatabase
import com.android.asteroidradar.database.PictureDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val network: NetworkRequest by lazy { NetworkRequest() }

    private val asteroidDao: AsteroidDao by lazy {
        AsteroidDatabase.getInstance(application).asteroidDao()
    }

    private val pictureDao: PictureDao by lazy {
        AsteroidDatabase.getInstance(application).pictureDao()
    }

    private val _state = MutableStateFlow(AsteroidState(true, emptyList()))
    val state = _state.asStateFlow()

    private val _pictureUrl = MutableLiveData(PictureState(null))
    val pictureUrl : LiveData<PictureState> = _pictureUrl

    private lateinit var cachedAsteroids: List<Asteroid>

    val loadingState = state.map { value -> value.loading }

    private val _navigateToDetailFragment = MutableLiveData<Asteroid>()
    val navigateToDetailFragment : LiveData<Asteroid>
        get() = _navigateToDetailFragment


    init {
        viewModelScope.launch {
            val asteroids = getAsteroids()
            _state.value = AsteroidState(false, asteroids)
            cachedAsteroids = asteroids

            _pictureUrl.value = PictureState(getPictureOfTheDay())
        }
    }

    private suspend fun getPictureOfTheDay() : PictureOfDay = withContext(Dispatchers.IO) {
        try {
            val response = network.service.getPictureOfTheDay()
            pictureDao.insertPicture(response)
            pictureDao.getPicture()
        } catch (e: Exception) {
            e.printStackTrace()
            pictureDao.getPicture()
        }
    }

    fun updateFilter(filter: ApiFilter){
        viewModelScope.launch {
            when(filter){
                ApiFilter.SHOW_DAY -> {
                    val asteroids = asteroidDao.getTodayAsteroids(getToday())
                    _state.value = AsteroidState(false, asteroids)
                }

                ApiFilter.SHOW_WEEK -> {
                    val asteroids = asteroidDao.getWeekAsteroids(getToday(), getSeventhDay())
                    _state.value = AsteroidState(false, asteroids)
                }

                else -> {
                    val asteroids = asteroidDao.getAllAsteroids()
                    _state.value = AsteroidState(false, asteroids)
                }
            }
        }
    }

    private suspend fun getAsteroids() : List<Asteroid> =  withContext(Dispatchers.IO) {
        try {

            val response = network.service.getAsteroids(
                getToday(),
                getSeventhDay()
            )

            val jsonObject = JsonParser.parseString(response.toString()).asJsonObject
            val jsonResult = JSONObject(jsonObject.toString())
            val listAsteroids = parseAsteroidsJsonResult(jsonResult)

            asteroidDao.insertAsteroids(listAsteroids)

            asteroidDao.getAllAsteroids()

        } catch (e: Exception){
            e.printStackTrace()
            asteroidDao.getAllAsteroids()
        }
    }

    fun onNavigateClicked(id: Asteroid){
        _navigateToDetailFragment.value = id
    }

    fun onNavigateToDetailFragment(){
        _navigateToDetailFragment.value = null
    }
}

data class AsteroidState(val loading: Boolean, val asteroids: List<Asteroid>)

data class PictureState(val pictureUrl: PictureOfDay?)

enum class ApiFilter(val value : String) { SHOW_WEEK("week"), SHOW_DAY("day"), SHOW_ALL("saved")}