package com.exam.appetiserchallenge.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteTrackModel(
    val trackId: Long = 0L,
    val isTrackFavorite: Boolean = false
)
