package com.elinmejorabletv.di

import androidx.room.Room
import com.elinmejorabletv.data.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "elinmejorabletv_db"
        ).build()
    }

    single { get<AppDatabase>().trackDao() }
}