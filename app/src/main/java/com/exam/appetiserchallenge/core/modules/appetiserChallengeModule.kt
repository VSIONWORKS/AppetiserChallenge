package com.exam.appetiserchallenge.core.modules

import com.exam.appetiserchallenge.data.repository.AppetiserChallengeRepositoryImpl
import com.exam.appetiserchallenge.domain.AppetiserChallengeRepository
import com.exam.appetiserchallenge.ui.viewModel.MainViewModel
import com.exam.appetiserchallenge.ui.viewModel.SearchTrackViewModel
import com.exam.appetiserchallenge.ui.viewModel.TrackViewViewModel
import com.exam.appetiserchallenge.utils.SharedPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Dependency module for ff:
 * [SharedPrefs],
 * [AppetiserChallengeRepository],
 * [MainViewModel],
 * [SearchTrackViewModel],
 * [MainViewModel]
 * */
val appetiserChallengeModule = module {
    factory{ SharedPrefs(androidContext()) }
    factory<AppetiserChallengeRepository> { AppetiserChallengeRepositoryImpl(get(), get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { SearchTrackViewModel(get()) }
    viewModel { TrackViewViewModel(get()) }
}

