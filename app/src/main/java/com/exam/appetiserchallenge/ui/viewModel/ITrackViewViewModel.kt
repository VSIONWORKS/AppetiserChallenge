package com.exam.appetiserchallenge.ui.viewModel

import com.exam.appetiserchallenge.data.model.FavoriteTrackModel
import com.exam.appetiserchallenge.data.model.TrackModel

/**
 * [TrackViewViewModel] interface for overriding of methods and variables
 * */
interface ITrackViewViewModel {

    fun saveCurrentScreen(track: TrackModel)
    fun saveFavoriteItem(favorite: FavoriteTrackModel)
}