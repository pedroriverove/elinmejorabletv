package com.elinmejorabletv.di

import com.elinmejorabletv.data.repository.TrackRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { TrackRepository(get(), get()) }
}