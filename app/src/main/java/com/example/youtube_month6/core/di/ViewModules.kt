package com.example.youtube_month6.core.di

import com.example.youtube_month6.ui.detail.DetailViewModel
import com.example.youtube_month6.ui.player.PlayerViewModel
import com.example.youtube_month6.ui.playlist.PlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModules = module {
    viewModel { PlaylistViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { PlayerViewModel(get()) }
}