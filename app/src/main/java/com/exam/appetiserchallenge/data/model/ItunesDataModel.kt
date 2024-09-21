package com.exam.appetiserchallenge.data.model

import kotlinx.serialization.Serializable
import java.util.Collections.emptyList

@Serializable
data class ItunesDataModel(
    val resultCount: Int = 0,
    val results: List<TrackModel> = emptyList()
)
