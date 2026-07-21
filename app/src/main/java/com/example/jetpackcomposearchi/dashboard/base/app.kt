package com.example.jetpackcomposearchi.dashboard.base

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {
    
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    
    companion object {
        lateinit var instance: App

        @JvmStatic
        fun get(): App {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Manual initialization is required when default initializer is removed.
        // We use a try-catch to avoid crashing if it's somehow initialized twice.
        try {
            WorkManager.initialize(this, workManagerConfiguration)
        } catch (e: IllegalStateException) {
            Log.w("App", "WorkManager already initialized")
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.INFO)
            .build()
}
