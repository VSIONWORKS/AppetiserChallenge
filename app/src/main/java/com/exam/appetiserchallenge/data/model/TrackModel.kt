package com.exam.appetiserchallenge.data.model

import androidx.room.Entity
import kotlinx.serialization.Serializable


@Serializable
@Entity(
    primaryKeys = ["trackId", "collectionId"],
    tableName = "track_table"
)
data class TrackModel(
    val wrapperType: String = "",
    val kind: String = "",
    val collectionId: Long = 0,
    val trackId: Long = 0,
    val artistName: String = "",
    val collectionName: String = "",
    val trackName: String = "",
    val collectionCensoredName: String = "",
    val trackCensoredName: String = "",
    val collectionArtistId: Long = 0,
    val collectionArtistViewUrl: String = "",
    val collectionViewUrl: String = "",
    val trackViewUrl: String = "",
    val previewUrl: String = "",
    val artworkUrl30: String = "",
    val artworkUrl60: String = "",
    val artworkUrl100: String = "",
    val collectionPrice: Double = 0.0,
    val trackPrice: Double = 0.0,
    val trackRentalPrice: Double = 0.0,
    val collectionHdPrice: Double = 0.0,
    val trackHdPrice: Double = 0.0,
    val trackHdRentalPrice: Double = 0.0,
    val releaseDate: String = "",
    val collectionExplicitness: String = "",
    val trackExplicitness: String = "",
    val trackCount: Int = 0,
    val trackNumber: Int = 0,
    val trackTimeMillis: Long = 0,
    val country: String = "",
    val currency: String = "",
    val primaryGenreName: String = "",
    val contentAdvisoryRating: String = "",
    val shortDescription: String = "",
    val longDescription: String = "",
    val hasITunesExtras: Boolean = true,
    var isFavorite: Boolean = false
)
