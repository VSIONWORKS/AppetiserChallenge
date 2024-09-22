package com.exam.appetiserchallenge.core

import android.app.Application
import com.exam.appetiserchallenge.core.modules.appModules
import org.koin.android.ext.koin.androidContext

/**
 * Dependency injection core setup using Koin
 * */
class AppetiserChallenge : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidContext(this@AppetiserChallenge)
            modules(appModules)
        }
    }
}