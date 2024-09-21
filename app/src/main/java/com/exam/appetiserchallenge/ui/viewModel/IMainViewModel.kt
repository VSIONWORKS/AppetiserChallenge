package com.exam.appetiserchallenge.ui.viewModel

import com.exam.appetiserchallenge.data.model.FavoriteTrackModel
import com.exam.appetiserchallenge.data.model.TrackModel
import com.exam.appetiserchallenge.utils.LoadState
import kotlinx.coroutines.flow.StateFlow

interface IMainViewModel {
    var isFirstLaunch: Boolean
    val loadState: StateFlow<LoadState>
    val lastScreen: StateFlow<TrackModel>
    val lastVisitDate: StateFlow<String>
    val itunesDataFromDatabase: StateFlow<List<TrackModel>>

    fun loadData()
    fun fetchItunesData()
    fun setTrackToFavorite(favorite: FavoriteTrackModel)
    fun getLastScreen()
    fun clearLastScreen()
    fun setLastVisitDate()
}