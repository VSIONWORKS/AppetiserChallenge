package com.exam.appetiserchallenge.ui.item

import android.view.View
import com.exam.appetiserchallenge.R
import com.exam.appetiserchallenge.data.model.TrackModel
import com.exam.appetiserchallenge.databinding.LayoutItemBinding
import com.exam.appetiserchallenge.utils.load
import com.exam.appetiserchallenge.utils.setTint
import com.xwray.groupie.viewbinding.BindableItem

/**
 * Class for Track Item displayed on the recyclerview list.
 * */
class TrackItem(
    var onClickFavorite: (Long, Boolean) -> Unit,
    var onClickView: (TrackModel) -> Unit
) : BindableItem<LayoutItemBinding>() {

    var track: TrackModel = TrackModel()

    private var isTrackFavorite: Boolean = false
    private var binding: LayoutItemBinding? = null

    override fun bind(viewBinding: LayoutItemBinding, position: Int) {
        binding = viewBinding.apply {
            with(track) {
                ivArt.load(artworkUrl100, placeHolder = R.drawable.ic_no_image)
                tvTitle.text = trackName
                tvGenre.text = primaryGenreName
                tvPrice.text = "$currency $trackPrice"
                ivFavorite.apply {
                    isTrackFavorite = isFavorite
                    toggleFavorite(isFavorite)
                    setOnClickListener {
                        onClickFavorite.invoke(trackId, !isTrackFavorite)
                        toggleFavorite(!isTrackFavorite)
                    }
                }
            }
            root.setOnClickListener {
                onClickView.invoke(track)
            }
        }
    }

    /**
     * Method for toggling the favorite status of a track.
     * */
    private fun LayoutItemBinding.toggleFavorite(toggleFavorite: Boolean) {
        isTrackFavorite = toggleFavorite
        track.isFavorite = toggleFavorite
        ivFavorite.setTint(if (toggleFavorite) R.color.secondary else R.color.base)
    }

    fun updateFavorite(isFavorite: Boolean) {
        binding?.apply {
            toggleFavorite(isFavorite)
        }
    }

    override fun getLayout(): Int = R.layout.layout_item

    override fun initializeViewBinding(view: View): LayoutItemBinding = LayoutItemBinding.bind(view)
}