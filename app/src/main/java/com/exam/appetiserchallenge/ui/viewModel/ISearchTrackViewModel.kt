package com.exam.appetiserchallenge.ui.viewModel

import com.exam.appetiserchallenge.data.model.FavoriteTrackModel
import com.exam.appetiserchallenge.data.model.TrackModel
import kotlinx.coroutines.flow.StateFlow

interface ISearchTrackViewModel {
    val itunesDataFromDatabase: StateFlow<List<TrackModel>>

    fun searchTrack(trackName: String)
    fun setTrackToFavorite(favorite: FavoriteTrackModel)
}