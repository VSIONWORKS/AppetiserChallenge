package com.exam.appetiserchallenge.domain

import com.exam.appetiserchallenge.data.model.FavoriteTrackModel
import com.exam.appetiserchallenge.data.model.TrackModel
import kotlinx.coroutines.flow.Flow

interface AppetiserChallengeRepository {

    suspend fun getItunesDataFromDatabase(): List<TrackModel>
    suspend fun getItunesData()
    suspend fun setTrackToFavorite(favorite: FavoriteTrackModel)
    suspend fun setLastVisitDate()
    suspend fun getLastVisitDate(): String
    suspend fun saveCurrentScreen(track: TrackModel)
    suspend fun getLastScreen(): TrackModel

    fun search(name: String): Flow<List<TrackModel>>
}