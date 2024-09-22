package com.exam.appetiserchallenge.core.modules

import com.exam.appetiserchallenge.data.database.TrackDatabase
import com.exam.appetiserchallenge.domain.AppetiserChallengeRepository
import com.exam.appetiserchallenge.ui.viewModel.MainViewModel
import com.exam.appetiserchallenge.ui.viewModel.SearchTrackViewModel
import com.exam.appetiserchallenge.utils.SharedPrefs
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Dependency module for [TrackDatabase]
 * This is the room database dependency injection setup
 * */
val databaseModule = module {
    // main database here
    single { TrackDatabase.getDatabase(androidApplication()) }
    // dao here
    single { get<TrackDatabase>().trackDao }
}