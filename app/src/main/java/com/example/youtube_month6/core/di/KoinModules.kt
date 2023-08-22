package com.example.youtube_month6.core.di

import com.example.youtube_month6.core.network.networkModule

val koinModules = listOf(
    networkModule,
    repositoryModules,
    viewModules,
    remoteDataSource
)