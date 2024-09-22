package com.exam.appetiserchallenge.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.exam.appetiserchallenge.data.model.FavoriteTrackModel
import com.exam.appetiserchallenge.data.model.TrackModel
import com.exam.appetiserchallenge.databinding.ActivityMainBinding
import com.exam.appetiserchallenge.ui.item.TrackItem
import com.exam.appetiserchallenge.ui.viewModel.MainViewModel
import com.exam.appetiserchallenge.utils.Constants.IS_FAVORITE
import com.exam.appetiserchallenge.utils.Constants.LAST_VISITED
import com.exam.appetiserchallenge.utils.Constants.NETWORK_ERROR
import com.exam.appetiserchallenge.utils.Constants.TRACK_DETAILS
import com.exam.appetiserchallenge.utils.Constants.TRACK_ID
import com.exam.appetiserchallenge.utils.Constants.TRACK_REFRESH_CODE
import com.exam.appetiserchallenge.utils.Constants.TRACK_RESULT_CODE
import com.exam.appetiserchallenge.utils.LoadState
import com.exam.appetiserchallenge.utils.collectOnChange
import com.exam.appetiserchallenge.utils.getFormattedDate
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()
    private val body = Section()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setupUi()
        loadItunesData()
    }

    /**
     * Initialize Search Button listener and Recyclerview setup.
     * */
    private fun ActivityMainBinding.setupUi() {

        ivSearch.setOnClickListener {
            activityLauncher.launch(Intent(this@MainActivity, SearchTrackActivity::class.java))
        }
        rvTrackList.apply {
            adapter = GroupAdapter<GroupieViewHolder>().apply {
                add(body)
            }
            layoutManager = LinearLayoutManager(context)
        }
    }

    private val activityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == TRACK_RESULT_CODE && result.data != null) {
            result.data?.let {
                val trackId = it.getLongExtra(TRACK_ID, 0L)
                val isFavorite = it.getBooleanExtra(IS_FAVORITE, false)
                mainViewModel.setTrackToFavorite(FavoriteTrackModel(trackId, isFavorite))
                updateRvItem(trackId, isFavorite)
            }
        } else if (result.resultCode == TRACK_REFRESH_CODE && result.data != null) {
            binding.rvTrackList.itemAnimator = DefaultItemAnimator()
            mainViewModel.loadData()
        }
        mainViewModel.clearLastScreen()
    }

    /**
     * Initialize setup and handing of observables from viewModel.
     * */
    private fun loadItunesData() {
        with(mainViewModel) {
            itunesDataFromDatabase.collectOnChange(this@MainActivity) {
                if (!isFirstLaunch and it.isEmpty()) {
                    fetchItunesData()
                } else {
                    val items = it.map { track ->
                        TrackItem(
                            onClickFavorite = { trackId, isFavorite ->
                                setTrackToFavorite(FavoriteTrackModel(trackId, isFavorite))
                            },
                            onClickView = { track ->
                                navigateToTrackDetails(track)
                            }
                        ).apply {
                            this.track = track
                        }
                    }
                    body.update(items)
                    binding.rvTrackList.setItemViewCacheSize(items.size)
                    binding.rvTrackList.itemAnimator = null
                }
            }
            lastScreen.collectOnChange(this@MainActivity) {
                if (it.trackId != 0L) {
                    navigateToTrackDetails(it)
                }
            }
            lastVisitDate.collectOnChange(this@MainActivity) {
                if (it.isNotEmpty() && it != Date().getFormattedDate()) {
                    binding.apply {
                        tvLastVisited.isVisible = true
                        tvLastVisited.text = "$LAST_VISITED $it"
                    }
                }
            }
            loadState.collectOnChange(this@MainActivity) {
                handleLoadState(it)
            }
        }
    }

    /**
     * Method for navigating to [TrackViewActivity]
     * when track is pressed or selected.
     * */
    private fun navigateToTrackDetails(track: TrackModel) {
        val intent = Intent(this, TrackViewActivity::class.java)
        val trackDetails = Json.encodeToString(track)
        intent.putExtra(TRACK_DETAILS, trackDetails)
        activityLauncher.launch(intent)
    }

    /**
     * Updates the specific item on recyclerview when status of favorite changes.
     * */
    private fun updateRvItem(trackId: Long, isFavorite: Boolean) {
        body.groups.filterIsInstance<TrackItem>().forEach {
            if (it.track.trackId == trackId) {
                it.updateFavorite(isFavorite)
            }
        }
    }

    /**
     * Handles the loading state
     * */
    private fun handleLoadState(state: LoadState) {
        binding.apply {
            when (state) {
                LoadState.Loading -> pbLoader.isVisible = true
                LoadState.Completed -> {
                    pbLoader.isVisible = false
                }
                LoadState.Error -> {
                    Toast.makeText(this@MainActivity, NETWORK_ERROR, Toast.LENGTH_LONG).show()
                    pbLoader.isVisible = false
                }
            }
        }
    }
}