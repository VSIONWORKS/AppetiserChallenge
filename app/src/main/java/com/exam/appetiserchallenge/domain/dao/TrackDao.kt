package com.exam.appetiserchallenge.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exam.appetiserchallenge.data.model.TrackModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tracks: List<TrackModel>)

    @Query("SELECT * FROM track_table")
    fun getTracks(): List<TrackModel>

    @Query("UPDATE track_table SET isFavorite =:isTrackFavorite WHERE trackId =:id")
    suspend fun updateTrackToFavorite(id: Long, isTrackFavorite: Boolean)

    @Query("SELECT * FROM track_table WHERE trackName LIKE :name")
    fun searchTrack(name: String): Flow<List<TrackModel>>
}