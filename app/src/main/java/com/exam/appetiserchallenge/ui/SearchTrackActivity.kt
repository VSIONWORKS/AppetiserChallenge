package com.exam.appetiserchallenge.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.exam.appetiserchallenge.data.model.FavoriteTrackModel
import com.exam.appetiserchallenge.data.model.TrackModel
import com.exam.appetiserchallenge.databinding.ActivitySearchTrackBinding
import com.exam.appetiserchallenge.ui.item.TrackItem
import com.exam.appetiserchallenge.ui.viewModel.SearchTrackViewModel
import com.exam.appetiserchallenge.utils.Contants
import com.exam.appetiserchallenge.utils.SearchEnum
import com.exam.appetiserchallenge.utils.collectOnChange
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchTrackActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchTrackBinding
    private val viewModel: SearchTrackViewModel by viewModel()
    private val body = Section()
    private var onItemClick = false

    private val activityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Contants.TRACK_RESULT_CODE && result.data != null) {
            result.data?.let {
                val trackId = it.getLongExtra(Contants.TRACK_ID, 0L)
                val isFavorite = it.getBooleanExtra(Contants.IS_FAVORITE, false)
                viewModel.setTrackToFavorite(FavoriteTrackModel(trackId, isFavorite))
                updateRvItem(trackId, isFavorite)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setupUi()
    }

    private fun ActivitySearchTrackBinding.setupUi() {

        ivBack.setOnClickListener {
            finish()
        }

        svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val title = newText ?: ""
                onItemClick = false
                viewModel.searchTrack(title)
                return false
            }
        })

        rvTrackList.apply {
            adapter = GroupAdapter<GroupieViewHolder>().apply {
                add(body)
            }
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
        }

        viewModel.itunesDataFromDatabase.collectOnChange(this@SearchTrackActivity) {
            val searchQuery = svSearch.query.toString()
            if (searchQuery.isEmpty()) {
                loadSearch(SearchEnum.DEFAULT)
            } else if (searchQuery.isNotEmpty() && it.isEmpty()) {
                loadSearch(SearchEnum.NO_RESULT)
            } else {
                loadSearch(SearchEnum.WITH_RESULT)
                if (!onItemClick) {
                    val items = it.map { track ->
                        TrackItem(
                            onClickFavorite = { trackId, isFavorite ->
                                onItemClick = true
                                viewModel.setTrackToFavorite(FavoriteTrackModel(trackId, isFavorite))
                            },
                            onClickView = { track ->
                                onItemClick = true
                                navigateToTrackDetails(track)
                            }
                        ).apply {
                            this.track = track
                        }
                    }
                    body.update(items)
                    binding.rvTrackList.setItemViewCacheSize(items.size)
                }
            }
        }
    }

    private fun ActivitySearchTrackBinding.loadSearch(status: SearchEnum) {
        layoutDefault.isVisible = false
        tvNoResult.isVisible = false
        rvTrackList.isVisible = false

        when (status) {
            SearchEnum.DEFAULT -> {
                layoutDefault.isVisible = true
            }
            SearchEnum.WITH_RESULT -> {
                rvTrackList.isVisible = true
            }
            SearchEnum.NO_RESULT -> {
                tvNoResult.isVisible = true
            }
        }
    }

    private fun navigateToTrackDetails(track: TrackModel) {
        val intent = Intent(this, TrackViewActivity::class.java)
        val trackDetails = Json.encodeToString(track)
        intent.putExtra(Contants.TRACK_DETAILS, trackDetails)
        activityLauncher.launch(intent)
    }

    private fun updateRvItem(trackId: Long, isFavorite: Boolean) {
        body.groups.filterIsInstance<TrackItem>().forEach {
            if (it.track.trackId == trackId) {
                it.updateFavorite(isFavorite)
            }
        }
    }

    override fun finish() {
        val i = Intent()
        setResult(Contants.TRACK_REFRESH_CODE, i)
        super.finish()
    }
}