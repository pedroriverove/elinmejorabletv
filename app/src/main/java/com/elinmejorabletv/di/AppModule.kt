package com.elinmejorabletv.di

import com.elinmejorabletv.data.preferences.UserPreferences
import com.elinmejorabletv.domain.usecase.ManageFavoritesUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModules = module {
    single { UserPreferences(androidContext()) }
    factory { ManageFavoritesUseCase(get(), get()) }
}

val allModules = listOf(
    apiModule,
    databaseModule,
    repositoryModule,
    viewModelModule,
    appModules
)