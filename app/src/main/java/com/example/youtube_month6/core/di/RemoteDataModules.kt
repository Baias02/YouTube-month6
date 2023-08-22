package com.example.youtube_month6.core.di

import com.example.youtube_month6.core.network.RemoteDataSource
import org.koin.dsl.module

val remoteDataSource = module {
    factory { RemoteDataSource(get()) }
}