package com.exam.appetiserchallenge.ui.viewModel

import com.exam.appetiserchallenge.base.BaseViewModel
import com.exam.appetiserchallenge.data.model.FavoriteTrackModel
import com.exam.appetiserchallenge.data.model.TrackModel
import com.exam.appetiserchallenge.domain.AppetiserChallengeRepository
import com.exam.appetiserchallenge.utils.flowTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow

class SearchTrackViewModel(private val repository: AppetiserChallengeRepository) : BaseViewModel(), ISearchTrackViewModel {

    override val itunesDataFromDatabase = MutableStateFlow<List<TrackModel>>(emptyList())

    override fun searchTrack(trackName: String) {
        safeLaunch(Dispatchers.IO) {
            val name = "%$trackName%"
            repository.search(name) flowTo itunesDataFromDatabase
        }
    }

    override fun setTrackToFavorite(favorite: FavoriteTrackModel) = safeLaunch(Dispatchers.IO) { repository.setTrackToFavorite(favorite) }
}