package com.elinmejorabletv.di

import com.elinmejorabletv.domain.usecase.GetActiveTracksUseCase
import com.elinmejorabletv.domain.usecase.GetStreamingUrlUseCase
import com.elinmejorabletv.ui.mobile.account.AccountViewModel
import com.elinmejorabletv.ui.mobile.favorites.FavoritesViewModel
import com.elinmejorabletv.ui.mobile.home.HomeViewModel
import com.elinmejorabletv.ui.mobile.schedule.ScheduleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    // UseCases
    factory { GetActiveTracksUseCase(get()) }
    factory { GetStreamingUrlUseCase(get()) }

    // ViewModels
    viewModel { HomeViewModel(get()) }
    viewModel { ScheduleViewModel() }
    viewModel { FavoritesViewModel(get()) }
    viewModel { AccountViewModel(get()) }
}