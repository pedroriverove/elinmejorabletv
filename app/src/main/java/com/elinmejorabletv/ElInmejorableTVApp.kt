package com.elinmejorabletv

import android.app.Application
import com.elinmejorabletv.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ElInmejorableTVApp : Application() {

    val currentUser = "pedroriverove"
    val currentDate: String

    init {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        currentDate = sdf.format(Date())
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ElInmejorableTVApp)
            modules(allModules)
        }
    }
}