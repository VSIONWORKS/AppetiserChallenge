package com.exam.appetiserchallenge.ui.viewModel

import com.exam.appetiserchallenge.base.BaseViewModel
import com.exam.appetiserchallenge.data.model.FavoriteTrackModel
import com.exam.appetiserchallenge.data.model.TrackModel
import com.exam.appetiserchallenge.domain.AppetiserChallengeRepository
import kotlinx.coroutines.Dispatchers

/**
 * Serves as ViewModel for TrackViewActivity.
 * */
class TrackViewViewModel(private val repository: AppetiserChallengeRepository) : BaseViewModel(), ITrackViewViewModel {

    /**
     * Method for saving the current screen
     * */
    override fun saveCurrentScreen(track: TrackModel) {
        safeLaunch(Dispatchers.IO) {
            repository.saveCurrentScreen(track)
        }
    }

    /**
     * Method for setting the favorite status of a selected track.
     * */
    override fun saveFavoriteItem(favorite: FavoriteTrackModel) {
        safeLaunch(Dispatchers.IO) {
            repository.setTrackToFavorite(favorite)
        }
    }

}