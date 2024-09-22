package com.exam.appetiserchallenge.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exam.appetiserchallenge.R
import com.exam.appetiserchallenge.data.model.FavoriteTrackModel
import com.exam.appetiserchallenge.data.model.TrackModel
import com.exam.appetiserchallenge.databinding.ActivityTrackViewBinding
import com.exam.appetiserchallenge.ui.viewModel.TrackViewViewModel
import com.exam.appetiserchallenge.utils.Contants.IS_FAVORITE
import com.exam.appetiserchallenge.utils.Contants.TRACK_DETAILS
import com.exam.appetiserchallenge.utils.Contants.TRACK_ID
import com.exam.appetiserchallenge.utils.Contants.TRACK_RESULT_CODE
import com.exam.appetiserchallenge.utils.load
import com.exam.appetiserchallenge.utils.setTint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrackViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrackViewBinding

    private val trackViewsViewModel: TrackViewViewModel by viewModel()

    private var isTrackFavorite = false
    private var trackId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val trackDetailsExtra = intent.getStringExtra(TRACK_DETAILS)
        trackDetailsExtra?.let {
            val trackDetails = Json.decodeFromString<TrackModel>(it)
            binding.displayTrack(trackDetails)
        }
    }

    private fun ActivityTrackViewBinding.displayTrack(trackDetails: TrackModel) {
        with(trackDetails) {
            this@TrackViewActivity.trackId = trackId
            isTrackFavorite = isFavorite
            toggleFavorite(isTrackFavorite)
            ivArt.load(artworkUrl100)
            tvTitle.text = trackName
            tvGenre.text = primaryGenreName
            tvPrice.text = "$currency $trackPrice"
            tvDescription.text = longDescription
        }

        ivFavorite.setOnClickListener {
            toggleFavorite(!isTrackFavorite)
            trackViewsViewModel.saveFavoriteItem(FavoriteTrackModel(trackId, isTrackFavorite))
            trackDetails.isFavorite = isTrackFavorite
            trackViewsViewModel.saveCurrentScreen(trackDetails)
        }

        ivBack.setOnClickListener {
            finish()
        }
        trackViewsViewModel.saveCurrentScreen(trackDetails)
    }

    private fun ActivityTrackViewBinding.toggleFavorite(toggleFavorite: Boolean) {
        isTrackFavorite = toggleFavorite
        ivFavorite.setTint(if (toggleFavorite) R.color.secondary else R.color.base)
    }

    override fun finish() {
        val i = Intent()
        i.putExtra(TRACK_ID, trackId)
        i.putExtra(IS_FAVORITE, isTrackFavorite)
        setResult(TRACK_RESULT_CODE, i)
        super.finish()
    }
}