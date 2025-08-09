package com.elinmejorabletv

import android.app.Application
import com.elinmejorabletv.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ElInmejorableTVApp : Application() {

    val currentUser = "pedroriverove"
    val currentDate = "2025-08-09"

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ElInmejorableTVApp)
            modules(allModules)
        }
    }
}