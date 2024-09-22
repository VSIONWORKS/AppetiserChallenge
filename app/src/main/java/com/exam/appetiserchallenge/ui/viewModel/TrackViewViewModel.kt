package com.exam.appetiserchallenge.ui.viewModel

import com.exam.appetiserchallenge.base.BaseViewModel
import com.exam.appetiserchallenge.data.model.FavoriteTrackModel
import com.exam.appetiserchallenge.data.model.TrackModel
import com.exam.appetiserchallenge.domain.AppetiserChallengeRepository
import kotlinx.coroutines.Dispatchers

class TrackViewViewModel(private val repository: AppetiserChallengeRepository) : BaseViewModel(), ITrackViewViewModel {

    override fun saveCurrentScreen(track: TrackModel) {
        safeLaunch(Dispatchers.IO) {
            repository.saveCurrentScreen(track)
        }
    }

    override fun saveFavoriteItem(favorite: FavoriteTrackModel) {
        safeLaunch(Dispatchers.IO) {
            repository.setTrackToFavorite(favorite)
        }
    }

}