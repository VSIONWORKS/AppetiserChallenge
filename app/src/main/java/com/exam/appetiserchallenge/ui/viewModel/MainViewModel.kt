package com.exam.appetiserchallenge.ui.viewModel

import com.exam.appetiserchallenge.base.BaseViewModel
import com.exam.appetiserchallenge.data.model.FavoriteTrackModel
import com.exam.appetiserchallenge.data.model.TrackModel
import com.exam.appetiserchallenge.domain.AppetiserChallengeRepository
import com.exam.appetiserchallenge.utils.LoadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Serves as main ViewModel for MainActivity.
 * */
class MainViewModel(private val repository: AppetiserChallengeRepository) : BaseViewModel(), IMainViewModel {

    private val _itunesDataFromDatabase = MutableStateFlow(listOf(TrackModel()))
    private val _lastScreen = MutableStateFlow(TrackModel())
    private val _lastVisitDate = MutableStateFlow(String())

    override var isFirstLaunch: Boolean = true
    override val loadState = MutableStateFlow<LoadState>(LoadState.Loading)

    override val lastScreen = _lastScreen.asStateFlow()
    override val lastVisitDate = _lastVisitDate.asStateFlow()
    override val itunesDataFromDatabase = _itunesDataFromDatabase.asStateFlow()

    /**
     * Initialized all methods that are needed when starting the app.
     * */
    init {
        getLastScreen()
        loadData()
        setLastVisitDate()
    }

    /**
     * Setup of observable variables to be observe on our ui.
     * Variables are using Kotlin Flow
     * */
    override fun loadData() {
        safeLaunch(Dispatchers.IO) {
            isFirstLaunch = false
            _itunesDataFromDatabase.value = repository.getItunesDataFromDatabase()
            _lastScreen.value = repository.getLastScreen()
            _lastVisitDate.value = repository.getLastVisitDate()
            loadState.value = LoadState.Completed
        }
    }

    /**
     * Method for initializing the fetching data from url and
     * saving to room database
     * */
    override fun fetchItunesData() {
        safeLaunch(Dispatchers.IO) {
            loadState.value = LoadState.Loading
            repository.getItunesData()
            loadData()
        }
    }

    /**
     * Method for saving the favorite status of a track.
     * */
    override fun setTrackToFavorite(favorite: FavoriteTrackModel) = safeLaunch(Dispatchers.IO) { repository.setTrackToFavorite(favorite) }

    /**
     * Method for getting the last screen saved.
     * */
    override fun getLastScreen() {
        safeLaunch(Dispatchers.IO) {
            loadState.value = LoadState.Loading
            repository.getLastScreen()
            loadState.value = LoadState.Completed
        }
    }

    /**
     * Method for removing the last screen saved
     * */
    override fun clearLastScreen() {
        safeLaunch(Dispatchers.IO) {
            repository.saveCurrentScreen(TrackModel())
        }
    }

    /**
     * Method for saving the last visit date.
     * */
    override fun setLastVisitDate() {
        safeLaunch(Dispatchers.IO) {
            repository.setLastVisitDate()
        }
    }

}