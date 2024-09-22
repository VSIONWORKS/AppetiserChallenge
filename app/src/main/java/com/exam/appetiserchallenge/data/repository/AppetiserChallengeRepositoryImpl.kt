package com.exam.appetiserchallenge.data.repository

import com.exam.appetiserchallenge.data.model.FavoriteTrackModel
import com.exam.appetiserchallenge.data.model.TrackModel
import com.exam.appetiserchallenge.data.service.ApiService
import com.exam.appetiserchallenge.domain.AppetiserChallengeRepository
import com.exam.appetiserchallenge.domain.dao.TrackDao
import com.exam.appetiserchallenge.utils.Contants.CURRENT_DATE
import com.exam.appetiserchallenge.utils.Contants.LAST_SCREEN
import com.exam.appetiserchallenge.utils.Contants.LAST_VISIT_DATE
import com.exam.appetiserchallenge.utils.SharedPrefs
import com.exam.appetiserchallenge.utils.get
import com.exam.appetiserchallenge.utils.getFormattedDate
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

/**
 * Main Repository Implementation with parameter [ApiService], [TrackDao] and [SharedPrefs]
 * */
class AppetiserChallengeRepositoryImpl(private val service: ApiService, private val trackDao: TrackDao, private val prefs: SharedPrefs) : AppetiserChallengeRepository {

    /**
     * Method for calling the function [getDataFromDatabase]
     * */
    override suspend fun getItunesDataFromDatabase(): List<TrackModel> {
        return getDataFromDatabase()
    }

    /**
     * Method for fetching data from the url.
     * This method is only triggered once and the data from response is
     * being save to room database which is being used on populating our list view.
     * */
    override suspend fun getItunesData() {
        val data = service.getItunesData()
        saveDataToDatabase(data.results)
    }

    /**
     * Method for getting the list of track from room database.
     * */
    private fun getDataFromDatabase(): List<TrackModel> {
        return trackDao.getTracks()
    }

    /**
     * Method for saving the fetch data from URL to Room database.
     * */
    private suspend fun saveDataToDatabase(tracks: List<TrackModel>) {
        trackDao.insert(tracks)
    }

    /**
     * Method for saving the specific track as favorite.
     * */
    override suspend fun setTrackToFavorite(favorite: FavoriteTrackModel) {
        trackDao.updateTrackToFavorite(favorite.trackId, favorite.isTrackFavorite)
    }

    /**
     * Method for checking and saving the last visit date.
     * */
    override suspend fun setLastVisitDate() {
        val actualCurrentDate = Date().getFormattedDate()
        val savedCurrentDate = prefs.get(CURRENT_DATE, "")
        if (savedCurrentDate != actualCurrentDate) {
            prefs.save(CURRENT_DATE, Date().getFormattedDate())
            prefs.save(LAST_VISIT_DATE, savedCurrentDate)
        }
    }

    /**
     * Method for getting the last visit date.
     * */
    override suspend fun getLastVisitDate(): String {
        return prefs.get(LAST_VISIT_DATE, "")
    }

    /**
     * Method for saving the last screen visited.
     * */
    override suspend fun saveCurrentScreen(track: TrackModel) {
        prefs.save(LAST_SCREEN, Json.encodeToString(track))
    }

    /**
     * Method for getting the last screen visited.
     * */
    override suspend fun getLastScreen(): TrackModel {
        var data: String = prefs.get(LAST_SCREEN, "")
        return if (data.isEmpty()) TrackModel()
        else
            Json.decodeFromString(data)
    }

    /**
     * Method for searching a track using track name.
     * */
    override fun search(name: String): Flow<List<TrackModel>> {
        return trackDao.searchTrack(name)
    }
}