package com.exam.appetiserchallenge.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.exam.appetiserchallenge.data.model.TrackModel
import com.exam.appetiserchallenge.domain.dao.TrackDao

@Database(entities = [TrackModel::class], version = 1)
abstract class TrackDatabase : RoomDatabase() {

    abstract val trackDao: TrackDao

    companion object {
        /**
         * We don't need to add additional checking for instance created since we are using koin to manage the instance creation.
         */
        fun getDatabase(context: Context): TrackDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                TrackDatabase::class.java,
                "track_database"
            ).build()
        }
    }
}