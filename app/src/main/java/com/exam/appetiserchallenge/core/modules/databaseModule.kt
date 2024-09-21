package com.exam.appetiserchallenge.core.modules

import com.exam.appetiserchallenge.data.database.TrackDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    // main database here
    single { TrackDatabase.getDatabase(androidApplication()) }
    // dao here
    single { get<TrackDatabase>().trackDao }
}